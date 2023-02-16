package com.thoughtworks.androidtrain.helloworld.compose

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val dataSource: DataSource,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData(listOf())

    val tweets: LiveData<List<Tweet>> = tweetList

    fun fetchTweets() {
        val subscribe: Disposable = dataSource
            .fetchTweets()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe { tweets ->
                tweetList.postValue(tweets)
            }
        compositeDisposable.add(subscribe)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}