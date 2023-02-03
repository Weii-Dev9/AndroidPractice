package com.thoughtworks.androidtrain.helloworld.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class TweetAdapter (private val tweets: List<Tweet>) : RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nickSender: TextView = itemView.findViewById(R.id.nickSender)
        val contentTweet: TextView = itemView.findViewById(R.id.contentTweet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val tweetView = inflater.inflate(R.layout.tweets_item_layout, null, false)
        // Return a new holder instance
        return ViewHolder(tweetView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val tweet: Tweet = tweets[position]
        //屏蔽error
        if (tweet.error != null || tweet.unknownError=="unknown error"){
            return
        }
        // Set item views based on your views and data model
        val nickView = holder.nickSender
        nickView.text = tweet.sender?.nick
        val tweetView = holder.contentTweet
        tweetView.text = tweet.content
    }

    override fun getItemCount(): Int {
        return tweets.size
    }
}