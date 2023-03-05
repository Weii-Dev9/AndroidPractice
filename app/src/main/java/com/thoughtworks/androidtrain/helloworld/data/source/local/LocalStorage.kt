@file:Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS")

package com.thoughtworks.androidtrain.helloworld.data.source.local

import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import kotlinx.coroutines.flow.Flow


interface LocalStorage {

    suspend fun getTweetsFromRaw(): List<Tweet>
    suspend fun getTweets(): List<Tweet>
    suspend fun updateTweets(tweets: List<Tweet>): Boolean

}