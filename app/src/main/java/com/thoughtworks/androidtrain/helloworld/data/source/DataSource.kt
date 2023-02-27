package com.thoughtworks.androidtrain.helloworld.data.source

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import io.reactivex.rxjava3.core.Flowable

interface DataSource {
    fun fetchTweets(): Flowable<List<Tweet>>
}