package com.thoughtworks.androidtrain.helloworld.utils

import okhttp3.OkHttpClient
import okhttp3.Request

class HttpUtils {
    companion object {
        fun httpGET(url: String): String? {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            return response.body?.string()
        }
    }
}