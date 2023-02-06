package com.thoughtworks.androidtrain.helloworld.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class TweetAdapter(private val tweets: List<Tweet>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        var nickSender: TextView = itemView.findViewById(R.id.nickSender)
        val contentTweet: TextView = itemView.findViewById(R.id.contentTweet)

    }
    class BottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val context = parent.context
//        val inflater = LayoutInflater.from(context)
//        // Inflate the custom layout   注意：root为parent时数据显示错误
//        val tweetView = inflater.inflate(R.layout.tweets_item_layout, null, false)
//        // Return a new holder instance
//        return TweetViewHolder(tweetView)
//        // Inflate the custom layout   注意：root为parent时数据显示错误
//        val tweetView = inflater.inflate(R.layout.tweets_last_item_layout, null, false)
//        // Return a new holder instance
//        return BottomViewHolder(tweetView)

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            THE_SECOND_VIEW -> BottomViewHolder(
                inflater.inflate(R.layout.tweets_last_item_layout,parent,false)
            )else -> TweetViewHolder(inflater.inflate(R.layout.tweets_item_layout,null,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Get the data model based on position
        // Set item views based on your views and data model
        if(getItemViewType(position) == THE_FIRST_VIEW){
            val tweetViewHolder = holder as TweetViewHolder
            val tweet: Tweet = tweets[position]
            tweetViewHolder.nickSender.text = tweet.sender?.nick
            tweetViewHolder.contentTweet.text = tweet.content

        }
    }

    override fun getItemCount(): Int {
        return tweets.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position<tweets.size) THE_FIRST_VIEW else THE_SECOND_VIEW
    }


    companion object {
        const val THE_FIRST_VIEW = 1
        const val THE_SECOND_VIEW = 2
    }
}