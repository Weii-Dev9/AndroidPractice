package com.thoughtworks.androidtrain.helloworld.data.source.local

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.data.model.Comment
import com.thoughtworks.androidtrain.helloworld.data.model.Image
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.CommentEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.ImageEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.TweetEntity
import com.thoughtworks.androidtrain.helloworld.utils.FileUtils
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.util.function.Consumer
import java.util.stream.Collectors

@SuppressLint("NotConstructor")
@Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS")
class LocalStorageImp constructor(private var context: Context) : LocalStorage {
    private lateinit var gson: Gson
    private val db: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "practice-db").build()

    override fun getTweetsFromRaw(): List<Tweet> {
        gson = Gson()
        //read json
        val tweetText: String? = FileUtils.readFileFromRaw(context, R.raw.tweets)
        return gson.fromJson<ArrayList<Tweet>?>(
            tweetText,
            object : TypeToken<List<Tweet?>?>() {}.type
        )
    }

    override fun getTweets(): Flowable<List<Tweet>> {
        return db.tweetDao().getAll().map { tweetsEntity ->
            //获取Model Tweet所有值
            val senderEntities: List<SenderEntity> = db.senderDao().getAll().blockingFirst()
            val imageEntities: List<ImageEntity> = db.imageDao().getAll().blockingFirst()
            val commentEntities: List<CommentEntity> = db.commentDao().getAll().blockingFirst()
            //赋值 Entity -> Model
            val tweets: MutableList<Tweet> = ArrayList()

            for (tweetEntity in tweetsEntity) {
                val tweet: Tweet = toTweet(tweetEntity)
                senderEntities.stream()
                    //过滤出与该tweet有关的Sender
                    .filter { senderEntity: SenderEntity -> senderEntity.id === tweetEntity.senderId }
                    .map(this::toSender)
                    .findFirst()
                    .ifPresent { sender: Sender -> tweet.sender = sender }

                tweet.images = (imageEntities.stream()
                    //过滤出tweet的images
                    .filter { imageEntity: ImageEntity -> imageEntity.id === tweetEntity.id }
                    //赋值
                    .map<Any> { imageEntity: ImageEntity ->
                        Image(
                            imageEntity.url!!
                        )
                    }.collect(Collectors.toList()) as List<Image>?)!!

                tweet.comments = commentEntities.stream()
                    //过滤出 tweet的comment
                    .filter { commentEntity: CommentEntity -> commentEntity.tweetId === tweetEntity.id }
                    .map<Any> { commentEntity: CommentEntity ->
                        senderEntities.stream()
                            //过滤出与该Comment有关的Sender
                            .filter { senderEntity: SenderEntity -> senderEntity.id === commentEntity.senderId }
                            .map(this::toSender)
                            .findFirst().orElse(null)?.let {
                                Comment(
                                    commentEntity.content,
                                    it
                                )
                            }

                    }.collect(Collectors.toList()) as List<Comment>
                tweets.add(tweet)
            }
            return@map tweets
        }
    }

    override fun updateTweets(tweets: List<Tweet>): Single<Boolean> {
        return Single.create { emitter ->
            db.clearAllTables()//delete data
            db.runInTransaction { //事务机制
                tweets.forEach(Consumer { tweet: Tweet ->
                    val tweetEntity: TweetEntity = toRoomTweet(tweet)
                    tweetEntity.senderId = insertRoomSender(tweet.sender)//Model tweet 转 Entity tweet
                    val tweetId: Long = db.tweetDao().insert(tweetEntity).blockingGet()//插入数据库
                    tweet.images.forEach { image ->
                        val imageEntity: ImageEntity = toRoomImage(image, tweetId)
                        db.imageDao().insert(imageEntity)?.blockingGet()
                    }
                    tweet.comments.forEach { comment ->
                        val commentEntity: CommentEntity =
                            toRoomComment(
                                comment,
                                tweetId,
                                insertRoomSender(comment.sender)
                            )
                        db.commentDao().insert(commentEntity)?.blockingGet()
                    }
                })
            }
            emitter.onSuccess(true)
        }
    }


    private fun toTweet(tweetEntity: TweetEntity): Tweet {
        val tweet: Tweet = Tweet()
        tweet.content = tweetEntity.content.toString()
        return tweet
    }

    private fun toSender(senderEntity: SenderEntity): Sender {
        val sender = Sender()
        sender.username = senderEntity.username
        sender.nick = senderEntity.nick
        sender.avatar = senderEntity.avatar
        return sender
    }

    private fun toRoomTweet(tweet: Tweet): TweetEntity {
        val tweetEntity = TweetEntity()
        tweetEntity.id = 0
        tweetEntity.content = tweet.content
        return tweetEntity
    }

    private fun insertRoomSender(sender: Sender?): Long {
        val senderEntity = toRoomSender(sender)
        return db.senderDao().insert(senderEntity).blockingGet()
    }

    private fun toRoomSender(sender: Sender?): SenderEntity {
        val senderEntity = SenderEntity()
        senderEntity.id = 0
        senderEntity.username = sender?.username
        senderEntity.nick = sender?.nick
        senderEntity.avatar = sender?.avatar
        return senderEntity
    }

    private fun toRoomImage(image: Image, tweetId: Long): ImageEntity {
        val imageEntity = ImageEntity()
        imageEntity.id = 0
        imageEntity.tweetId = tweetId
        imageEntity.url = image.url
        return imageEntity
    }

    private fun toRoomComment(comment: Comment, tweetId: Long, senderId: Long): CommentEntity {
        val commentEntity = CommentEntity()
        commentEntity.id = 0
        commentEntity.tweetId = tweetId
        commentEntity.senderId = senderId
        commentEntity.content = comment.content
        return commentEntity
    }

}