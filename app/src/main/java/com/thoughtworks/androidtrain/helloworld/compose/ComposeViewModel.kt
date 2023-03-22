package com.thoughtworks.androidtrain.helloworld.compose

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData(listOf())

    val tweets: LiveData<List<Tweet>> = tweetList

    fun fetchTweets() {
        viewModelScope.launch {
            tweetList.postValue(dataSource.fetchTweets())
        }
    }
}


