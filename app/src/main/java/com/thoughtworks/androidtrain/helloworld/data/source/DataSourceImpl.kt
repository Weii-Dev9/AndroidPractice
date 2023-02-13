package com.thoughtworks.androidtrain.helloworld.data.source

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.LocalStorageImpl
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.remote.RemoteDataImpl
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.stream.Collectors


class DataSourceImpl constructor(context: Context) : DataSource {
    private lateinit var db: AppDatabase
    private val localStorage = LocalStorageImpl(context)
    private val remoteData = RemoteDataImpl()
    private val tweetsUrl: String = "https://tw-mobile-xian.github.io/moments-data/tweets.json"
    private val client: OkHttpClient = OkHttpClient()
    private lateinit var request: Request
    private val gson = Gson()

    override fun fetchTweets(): Flowable<List<Tweet>> {
        remoteData.fetchTweets()
            .subscribeOn(Schedulers.io())
            .subscribe { tweets ->
                localStorage.updateTweets(tweets).subscribeOn(Schedulers.io()).subscribe()
            }
        //每次获取前都更新数据库，保证每次获取最新数据
        return localStorage.getTweets()
    }


    //协程
    override suspend fun fetchTweetsCoroutine(): List<Tweet> {
        return requestTweets()
    }

    private fun requestTweets(): List<Tweet> {
        request = Request.Builder().url(tweetsUrl).build()
        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            val tweetStr = response.body?.string()
            gson.fromJson(
                tweetStr,
                object : TypeToken<List<Tweet>>() {}.type
            )
        } else {
            Log.e(TAG, "Request Tweets failed")
            emptyList()
        }
    }

    companion object {
        private const val TAG = "DataSourceImp"
    }
}