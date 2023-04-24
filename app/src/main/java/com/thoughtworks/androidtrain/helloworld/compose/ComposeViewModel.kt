package com.thoughtworks.androidtrain.helloworld.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val dataSource: DataSource,
) : ViewModel() {

    private val _tweetState = mutableStateOf(Tweet(sender = Sender(isCurrentUser = true)))
    val tweet: State<Tweet> = _tweetState

    private var _tweetsState = mutableStateOf<List<Tweet>>(emptyList())
    val tweets: State<List<Tweet>> = _tweetsState

    fun insertData(tweet: Tweet) {
        viewModelScope.launch {
            dataSource.insertTweet(tweet)
        }
    }

    fun fetchTweets() {
        viewModelScope.launch {
            dataSource.fetchTweets().collect { tweet ->
                _tweetsState.value = tweet
            }
        }
    }
}


