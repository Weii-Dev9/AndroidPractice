package com.thoughtworks.androidtrain.helloworld.data.source

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.LocalStorage
import com.thoughtworks.androidtrain.helloworld.data.source.remote.RemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DataSourceImpl @Inject constructor(
    private val localStorage: LocalStorage,
    private val remoteStorage: RemoteData
) : DataSource {

    override suspend fun fetchRemoteTweets() = withContext(Dispatchers.IO) {
        val fetchRemoteTweets = remoteStorage.fetchTweets()

        fetchRemoteTweets.forEach { tweet ->
            localStorage.insertTweet(tweet)
        }
    }

    override fun fetchTweets(): Flow<List<Tweet>> {
        return localStorage.getTweets().map { tweets ->
            tweets.filter { tweet ->
                tweet.content.isNotBlank()
            }
        }
    }

    override suspend fun insertTweet(tweet: Tweet) {
        withContext(Dispatchers.IO) {
            localStorage.insertTweet(tweet)
        }
    }
}
