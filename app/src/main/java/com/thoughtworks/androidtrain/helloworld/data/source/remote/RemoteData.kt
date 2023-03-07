package com.thoughtworks.androidtrain.helloworld.data.source.remote

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

interface RemoteData {
    suspend fun fetchTweets(): List<Tweet>
}