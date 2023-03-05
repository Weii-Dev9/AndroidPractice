package com.thoughtworks.androidtrain.helloworld.data.source

import android.content.Context
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.LocalStorageImpl
import com.thoughtworks.androidtrain.helloworld.data.source.remote.RemoteDataImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext


class DataSourceImpl constructor(context: Context) : DataSource {
    private val localStorage = LocalStorageImpl(context)
    private val remoteData = RemoteDataImpl()

    override suspend fun fetchTweets() = flow {
        val filteredTweets = remoteData.fetchTweets().filter { tweet ->
            tweet.error == null && tweet.unknownError == null
        }
        localStorage.updateTweets(filteredTweets)
        emit(localStorage.getTweets())
    }
}

