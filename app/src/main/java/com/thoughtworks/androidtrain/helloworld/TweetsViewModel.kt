package com.thoughtworks.androidtrain.helloworld

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImpl
import com.thoughtworks.androidtrain.helloworld.utils.Dependency
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.SchedulerProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

@SuppressLint("StaticFieldLeak")
class TweetsViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var dataSource: DataSource
    private lateinit var schedulerProvider: SchedulerProvider


    val tweetList: MutableLiveData<List<Tweet>> by lazy {
        MutableLiveData<List<Tweet>>()
    }

    fun setDependencies(dataSource: DataSource, schedulerProvider: SchedulerProvider) {
        this.dataSource = dataSource
        this.schedulerProvider = schedulerProvider
    }

    fun fetchTweets() {
        val subscribe: Disposable = dataSource
            .fetchTweets()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe { tweets -> tweetList.postValue(tweets) }
        compositeDisposable.add(subscribe)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}