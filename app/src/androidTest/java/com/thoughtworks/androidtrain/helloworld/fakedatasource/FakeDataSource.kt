package com.thoughtworks.androidtrain.helloworld.fakedatasource

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataSource : DataSource {

    private val tweetItems = mutableListOf<Tweet>()

    override suspend fun insertTweet(tweet: Tweet) {
        tweetItems.add(tweet)
    }

    override fun fetchTweets(): Flow<List<Tweet>> {
        return flowOf(tweetItems)
    }

}
