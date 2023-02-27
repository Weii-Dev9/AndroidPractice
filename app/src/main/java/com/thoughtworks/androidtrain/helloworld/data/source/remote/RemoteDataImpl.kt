package com.thoughtworks.androidtrain.helloworld.data.source.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

class RemoteDataImpl : RemoteData {
    private val tweetsUrl = "https://tw-mobile-xian.github.io/moments-data/tweets.json"

    private val client = OkHttpClient()

    private val gson = Gson()

    override fun fetchTweets(): Single<List<Tweet>> {
        return Single.create { emitter: SingleEmitter<List<Tweet>> ->
            val request: Request = Request.Builder()
                .url(tweetsUrl)
                .build()
            try {
                client.newCall(request).execute().use { response ->
                    val tweetsStr = response.body?.string()
                    val tweets: List<Tweet> =
                        gson.fromJson(
                            tweetsStr,
                            object : TypeToken<List<Tweet>>() {}.type
                        )
                    emitter.onSuccess(tweets)
                }
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
    }
}
