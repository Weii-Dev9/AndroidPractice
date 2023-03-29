package com.thoughtworks.androidtrain.helloworld.data.source.local

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.thoughtworks.androidtrain.helloworld.data.model.Comment
import com.thoughtworks.androidtrain.helloworld.data.model.Image
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.CommentEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.ImageEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.TweetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SuppressLint("NotConstructor")
@Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS")
class LocalStorageImpl constructor(context: Context) : LocalStorage {
    private val db: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "practice-db").build()

    override fun getTweets(): Flow<List<Tweet>> {
        return db.tweetDao().getAll().map { tweetEntities ->
            tweetEntities.map { tweetEntity ->
                val tweet: Tweet = toTweet(tweetEntity)

                val sender = db.senderDao().getById(tweetEntity.senderId).let { toSender(it) }
                tweet.sender = sender

                val images: MutableList<Image> = ArrayList()
                db.imageDao().getByTweetId(tweetEntity.id).forEach { imageEntity ->
                    images.add(Image(imageEntity.url!!))
                }
                tweet.images = images
                tweet
            }
        }
    }

    override suspend fun updateTweets(tweets: List<Tweet>) {
        tweets.forEach { tweet ->
            val tweetEntity: TweetEntity = toRoomTweet(tweet)
            //Model tweet 转 Entity tweet
            tweetEntity.senderId =
                insertRoomSender(tweet.sender)
            //插入数据库
            val tweetId: Long = db.tweetDao().insert(tweetEntity)

            tweet.images.forEach { image ->
                val imageEntity: ImageEntity = toRoomImage(image, tweetId)
                db.imageDao().insert(imageEntity)
            }

            tweet.comments.forEach { comment ->
                val commentEntity: CommentEntity = toRoomComment(
                    comment, tweetId, insertRoomSender(comment.sender)
                )
                db.commentDao().insert(commentEntity)
            }
        }
    }

    override suspend fun insertTweet(tweet: Tweet): Long {
        val tweetEntity = toRoomTweet(tweet)
        tweetEntity.senderId =
            insertRoomSender(tweet.sender)
        val tweetId: Long = db.tweetDao().insert(tweetEntity)

        tweet.images.forEach { image ->
            val imageEntity: ImageEntity = toRoomImage(image, tweetId)
            db.imageDao().insert(imageEntity)
        }

        tweet.comments.forEach { comment ->
            val commentEntity: CommentEntity = toRoomComment(
                comment, tweetId, insertRoomSender(comment.sender)
            )
            db.commentDao().insert(commentEntity)
        }
        return tweetId
    }

    private fun toTweet(tweetEntity: TweetEntity): Tweet {
        val tweet = Tweet()
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

    private suspend fun insertRoomSender(sender: Sender?): Long {
        val senderEntity = toRoomSender(sender)
        return db.senderDao().insert(senderEntity)
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
