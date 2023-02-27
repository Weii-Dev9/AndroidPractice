package com.thoughtworks.androidtrain.helloworld

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class TweetsViewModel @Inject constructor(
) : ViewModel() {
    private lateinit var dataSource: DataSource
    private lateinit var schedulerProvider: SchedulerProvider

    private val compositeDisposable = CompositeDisposable()

    val tweetList: MutableLiveData<List<Tweet>> = MutableLiveData(listOf())

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

    fun setDependencies(dataSource: DataSource, schedulerProvider: SchedulerProvider) {
        this.dataSource = dataSource
        this.schedulerProvider = schedulerProvider
    }
}