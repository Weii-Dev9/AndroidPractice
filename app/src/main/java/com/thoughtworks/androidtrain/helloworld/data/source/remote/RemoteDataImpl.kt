package com.thoughtworks.androidtrain.helloworld.data.source.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteDataImpl : RemoteData {
    private val tweetsUrl = "https://tw-mobile-xian.github.io/moments-data/tweets.json"

    private val client = OkHttpClient()
    private lateinit var request: Request
    private val gson = Gson()

    override suspend fun fetchTweets(): List<Tweet> {
        return withContext(Dispatchers.IO) {
            request = Request.Builder().url(tweetsUrl).build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
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
    }

    companion object {
        private const val TAG = "DataSourceImp"
    }
}
