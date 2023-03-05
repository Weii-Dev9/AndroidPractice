package com.thoughtworks.androidtrain.helloworld.compose

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val dataSource: DataSource,
) : ViewModel() {

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData(listOf())

    val tweets: LiveData<List<Tweet>> = tweetList

    suspend fun fetchTweets() {
        dataSource.fetchTweets().collect { ts ->
            tweetList.postValue(ts)
        }
    }
}


