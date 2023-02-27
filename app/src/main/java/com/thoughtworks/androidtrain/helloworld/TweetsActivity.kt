package com.thoughtworks.androidtrain.helloworld

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.helloworld.adapters.TweetAdapter
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TweetsActivity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var adapter: TweetAdapter
    private lateinit var dataSource: DataSource
    private val context: Context = this

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
        dataSource = DataSourceImpl(this)
        adapter = TweetAdapter(this)
        fetchData1()
    }

    private fun fetchData1() {//RxJava
        val subscribe: Disposable = dataSource
            .fetchTweets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tweets -> adapter.setData(tweets) }
            ) { throwable ->
                Toast.makeText(
                    context,
                    throwable.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        compositeDisposable.add(subscribe)
    }

    private fun fetchData2() {//CoroutineScope
        GlobalScope.launch(Dispatchers.IO) {
            val tweets = dataSource.fetchTweetsCoroutine()
            withContext(Dispatchers.Main) {
                adapter.setData(tweets)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}