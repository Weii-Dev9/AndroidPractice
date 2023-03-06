package com.thoughtworks.androidtrain.helloworld.data.source

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

interface DataSource {
    suspend fun fetchTweets(): List<Tweet>
}