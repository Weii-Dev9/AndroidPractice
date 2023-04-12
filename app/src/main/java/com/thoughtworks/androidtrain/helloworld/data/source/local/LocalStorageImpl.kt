package com.thoughtworks.androidtrain.helloworld.data.source.local

import android.annotation.SuppressLint
import com.thoughtworks.androidtrain.helloworld.data.model.Image
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.toEntity
import com.thoughtworks.androidtrain.helloworld.data.source.toImage
import com.thoughtworks.androidtrain.helloworld.data.source.toSender
import com.thoughtworks.androidtrain.helloworld.data.source.toTweet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@SuppressLint("NotConstructor")
@Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS")
class LocalStorageImpl @Inject constructor(private val db: AppDatabase) : LocalStorage {

    override fun getTweets(): Flow<List<Tweet>> {
        return db.tweetDao().getAll().map { tweetEntities ->
            tweetEntities.map { tweetEntity ->
                val tweet: Tweet = tweetEntity.toTweet()

                val sender = db.senderDao().getById(tweetEntity.senderId).toSender()
                tweet.sender = sender

                val images: MutableList<Image> = ArrayList()
                db.imageDao().getByTweetId(tweetEntity.id).forEach { imageEntity ->
                    images.add(imageEntity.toImage())
                }
                tweet.images = images
                tweet
            }
        }
    }

    override suspend fun insertTweet(tweet: Tweet) {
        val senderEntity = db.senderDao().getByIsUser(1)
        tweet.sender = senderEntity.toSender()
        val tweetEntity = tweet.toEntity()
        db.tweetDao().insert(tweetEntity)

        tweet.images.forEach { image ->
            val imageEntity = image.toEntity(tweet.id)
            db.imageDao().insert(imageEntity)
        }
    }

    override suspend fun insertRemoteTweet(tweet: Tweet) {
        val tweetEntity = tweet.toEntity()
        val senderEntity = tweet.sender?.toEntity()
        senderEntity?.let {
            db.senderDao().insert(it)
            db.tweetDao().insert(tweetEntity)
            tweet.comments.forEach { comment ->
                db.senderDao().insert(comment.sender.toEntity())
                val commentEntity = comment.toEntity(tweetEntity.id)
                db.commentDao().insert(commentEntity)
            }
        }

        tweet.images.forEach { image ->
            val imageEntity = image.toEntity(tweetEntity.id)
            db.imageDao().insert(imageEntity)
        }

    }

}
