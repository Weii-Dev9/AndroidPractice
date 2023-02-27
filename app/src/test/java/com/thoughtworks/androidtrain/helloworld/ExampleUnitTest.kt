package com.thoughtworks.androidtrain.helloworld

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.DataSource
import com.thoughtworks.androidtrain.helloworld.utils.schedulers.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var dataSource: DataSource
    private lateinit var schedulerProvider: SchedulerProvider

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun beforeEach() {
        dataSource = Mockito.mock(DataSource::class.java)
        schedulerProvider = Mockito.mock(SchedulerProvider::class.java)
    }

    @Test
    fun fetchTweets_test() {

        val tweets: MutableList<Tweet> = ArrayList()
        val tweet = Tweet("Jack", null, emptyList(), emptyList(), null, null)
        tweets.add(tweet)

        `when`(dataSource.fetchTweets()).thenReturn(Flowable.just(tweets))
        `when`(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        `when`(schedulerProvider.ui()).thenReturn(Schedulers.trampoline())

        val tweetsViewModel = TweetsViewModel()
        tweetsViewModel.setDependencies(dataSource, schedulerProvider)
        tweetsViewModel.fetchTweets()

        Assert.assertEquals(1, tweetsViewModel.tweetList.value?.size)
        Assert.assertEquals("Jack", tweetsViewModel.tweetList.value?.get(0)?.content)
    }
}