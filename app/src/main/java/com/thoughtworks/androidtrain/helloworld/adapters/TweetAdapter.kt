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
@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class TweetAdapter(private val tweets: List<Tweet>) :
    RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nickSender: TextView = itemView.findViewById(R.id.nickSender)
        val contentTweet: TextView = itemView.findViewById(R.id.contentTweet)
        fun bind(position: Int) {
            val recyclerViewModel = tweets[position]
            if (recyclerViewModel.error != null || recyclerViewModel.unknownError == "unknown error") {
                return
            }
            nickSender.text = recyclerViewModel.sender?.nick
            contentTweet.text = recyclerViewModel.content
            //屏蔽error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
//        val inflater = LayoutInflater.from(context)
//        // Inflate the custom layout   注意：root为parent时数据显示错误
//        val tweetView = inflater.inflate(R.layout.tweets_item_layout, null, false)
//        // Return a new holder instance
//        return TweetViewHolder(tweetView)
//        // Inflate the custom layout   注意：root为parent时数据显示错误
//        val tweetView = inflater.inflate(R.layout.tweets_last_item_layout, null, false)
//        // Return a new holder instance
//        return BottomViewHolder(tweetView)
        val layout = when (viewType) {
            THE_FIRST_VIEW -> R.layout.tweets_item_layout
            THE_SECOND_VIEW -> R.layout.tweets_last_item_layout
            else -> throw IllegalArgumentException("Invalid type")
        }

        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, null, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        // Set item views based on your views and data model
        holder.bind(position)
    }
    override fun getItemViewType(position: Int): Int {
        return if(tweets[position].type==2){
            THE_SECOND_VIEW
        }else{
            THE_FIRST_VIEW
        }
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    companion object {
        const val THE_FIRST_VIEW = 1
        const val THE_SECOND_VIEW = 2
    }
}