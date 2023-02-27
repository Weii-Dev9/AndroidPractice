@file:Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS")

package com.thoughtworks.androidtrain.helloworld.data.source.local

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


interface LocalStorage {

    fun getTweetsFromRaw(): List<Tweet>
    fun getTweets(): Flowable<List<Tweet>>
    fun updateTweets(tweets: List<Tweet>): Single<Boolean>

}