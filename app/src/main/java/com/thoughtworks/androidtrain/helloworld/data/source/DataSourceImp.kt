package com.thoughtworks.androidtrain.helloworld.data.source

import android.content.Context
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.LocalStorageImp
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

class DataSourceImp constructor(context: Context) : DataSource {
    private lateinit var db: AppDatabase
    private val localStorage = LocalStorageImp(context)


    override fun fetchTweets(): Flowable<List<Tweet>> {
        localStorage.updateTweets(localStorage.getTweetsFromRaw())
            .subscribeOn(Schedulers.io())
            .subscribe({ aBoolean -> }) { throwable -> }

        //每次获取前都更新数据库，保证每次获取最新数据
        return localStorage.getTweets()
    }
    
}