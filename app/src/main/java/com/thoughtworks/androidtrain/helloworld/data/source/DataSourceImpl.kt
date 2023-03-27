package com.thoughtworks.androidtrain.helloworld.data.source

import android.content.Context
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.LocalStorageImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class DataSourceImpl constructor(context: Context) : DataSource {
    private val localStorage = LocalStorageImpl(context)

    override fun fetchTweets(): Flow<List<Tweet>> {
        return localStorage.getTweets().map { tweets ->
            tweets.filter { tweet ->
                tweet.content.isNotBlank()
            }
        }
    }

    override suspend fun insertTweet(tweet: Tweet) {
        withContext(Dispatchers.IO) {
            tweet.sender = Sender(nick = "Weii", username = "Weii", avatar = "own pic")
            localStorage.insertTweet(tweet)
        }
    }
}

