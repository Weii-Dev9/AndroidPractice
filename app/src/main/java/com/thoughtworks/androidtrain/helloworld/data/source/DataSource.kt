package com.thoughtworks.androidtrain.helloworld.data.source

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun fetchTweets(): Flow<List<Tweet>>
    suspend fun insertTweet(tweet: Tweet)
}
