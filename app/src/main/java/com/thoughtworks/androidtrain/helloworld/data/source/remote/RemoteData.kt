package com.thoughtworks.androidtrain.helloworld.data.source.remote

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import io.reactivex.rxjava3.core.Single

interface RemoteData {
    fun fetchTweets(): Single<List<Tweet>>
}