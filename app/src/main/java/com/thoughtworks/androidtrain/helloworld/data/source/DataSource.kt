package com.thoughtworks.androidtrain.helloworld.data.source

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun fetchRemoteTweets()
    fun fetchTweets(): Flow<List<Tweet>>
    suspend fun insertTweet(tweet: Tweet)
}
