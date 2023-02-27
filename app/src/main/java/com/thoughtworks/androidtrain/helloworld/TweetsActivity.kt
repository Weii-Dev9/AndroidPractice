package com.thoughtworks.androidtrain.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.helloworld.adapters.TweetAdapter
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImp
import com.thoughtworks.androidtrain.helloworld.utils.Dependency
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class TweetsActivity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var adapter: TweetAdapter
    private lateinit var dataSource: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_layout)
        initData()
        initUI()
    }

    private fun initUI() {
        val rvContents = findViewById<RecyclerView>(R.id.rvContent)
        rvContents.adapter = adapter
        rvContents.layoutManager = LinearLayoutManager(this)
    }

    private fun initData() {
        dataSource = DataSourceImp(this)
        val subscribe: Disposable? = dataSource.fetchTweets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tweets ->
                adapter.setData(tweets)
            }
        compositeDisposable.add(subscribe)
        adapter = TweetAdapter(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}