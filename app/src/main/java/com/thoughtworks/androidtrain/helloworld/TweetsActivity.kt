package com.thoughtworks.androidtrain.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.helloworld.adapters.TweetAdapter
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class TweetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_layout)
        initUI()
    }

    private fun initUI() {
        // Lookup the recyclerview in activity layout
        val rvContents = findViewById<RecyclerView>(R.id.rvContent)
        // Initialize tweets
        val gson = Gson()
        val tweet1: String? = readFileFromRaw(R.raw.tweets)
        val tweets2: ArrayList<Tweet> =
            gson.fromJson(tweet1, object : TypeToken<List<Tweet?>?>() {}.type)

        // Create adapter passing in the sample user data
        val adapter = TweetAdapter(tweets2,applicationContext)
        // Attach the adapter to the recyclerview to populate items
        rvContents.adapter = adapter
        // Set layout manager to position the items
        rvContents.layoutManager = LinearLayoutManager(this)
        // That's all!
    }

    private fun readFileFromRaw(rawId:Int): String? {
        val str: InputStream = resources.openRawResource(rawId)
        val reader = BufferedReader(InputStreamReader(str))
        var jsonStr: String? = ""
        var line: String? = ""
        try {
            while (reader.readLine().also { line = it } != null) {
                jsonStr += line
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return jsonStr
    }
}