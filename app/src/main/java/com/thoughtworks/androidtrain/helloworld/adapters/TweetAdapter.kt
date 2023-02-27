package com.thoughtworks.androidtrain.helloworld.adapters

import android.content.Context
import com.thoughtworks.androidtrain.helloworld.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class TweetAdapter(private val tweets: List<Tweet>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext: Context = context

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nickSender: TextView = itemView.findViewById(R.id.nickSender)
        val contentTweet: TextView = itemView.findViewById(R.id.contentTweet)
        val avatar: ImageView = itemView.findViewById(R.id.avatar)

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
        return when (viewType) {
            THE_SECOND_VIEW -> BottomViewHolder(
                inflater.inflate(R.layout.tweets_last_item_layout, parent, false)
            )
            else -> TweetViewHolder(inflater.inflate(R.layout.tweets_item_layout, null, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Get the data model based on position
        // Set item views based on your views and data model
        if (getItemViewType(position) == THE_FIRST_VIEW) {
            val tweetViewHolder = holder as TweetViewHolder
            val tweet: Tweet = tweets[position]
            tweetViewHolder.nickSender.text = tweet.sender?.nick
            tweetViewHolder.contentTweet.text = tweet.content

            if (tweet.sender?.avatar == null) {
                tweetViewHolder.avatar.setImageResource(R.mipmap.avatar)
            } else {
                Glide.with(mContext)
                    .load(tweet.sender.avatar)
                    .centerCrop()
                    .into(tweetViewHolder.avatar)
            }
        }
    }

    override fun getItemCount(): Int {
        return tweets.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < tweets.size) THE_FIRST_VIEW else THE_SECOND_VIEW
    }


    companion object {
        const val THE_FIRST_VIEW = 1
        const val THE_SECOND_VIEW = 2
    }
}