package com.thoughtworks.androidtrain.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.helloworld.adapters.TweetAdapter
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.data.source.DataSourceImpl
import com.thoughtworks.androidtrain.helloworld.utils.Dependency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TweetsActivity : AppCompatActivity() {
    private lateinit var adapter: TweetAdapter
    private lateinit var dataSource: DataSource
    private lateinit var tweetsViewModel: TweetsViewModel
    private lateinit var dependency: Dependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_layout)
        initialize()
    }

    private fun initialize() {
        dependency = (application as HelloApplication).getDependency()
        initViewModel()
        initUI()
    }


    private fun initViewModel() {
        tweetsViewModel = ViewModelProvider(this)[TweetsViewModel::class.java]
        dependency.getDataSource()
            ?.let { tweetsViewModel.setDependencies(it,dependency.getSchedulerProvider()) }
        tweetsViewModel.tweetList.observe(this) { tweets -> adapter.setData(tweets) }//键听ViewModel中的tweetList
        tweetsViewModel.fetchTweets()
    }

    private fun initUI() {
        val rvContents = findViewById<RecyclerView>(R.id.rvContent)
        adapter = TweetAdapter(this)//初始化adapter
        rvContents.adapter = adapter
        rvContents.layoutManager = LinearLayoutManager(this)
    }


//    private fun fetchData1() {//RxJava
//        val subscribe: Disposable = dataSource
//            .fetchTweets()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { tweets -> adapter.setData(tweets) }
//            ) { throwable ->
//                Toast.makeText(
//                    context,
//                    throwable.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        compositeDisposable.add(subscribe)
//    }

    private fun fetchData2() {//CoroutineScope 协程
        dataSource = dependency.getDataSource()!!
        GlobalScope.launch(Dispatchers.IO) {
            val tweets = dataSource.fetchTweetsCoroutine()
            withContext(Dispatchers.Main) {
                adapter.setData(tweets)
            }
        }
    }

}