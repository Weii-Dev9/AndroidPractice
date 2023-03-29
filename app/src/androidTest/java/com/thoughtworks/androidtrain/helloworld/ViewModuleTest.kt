package com.thoughtworks.androidtrain.helloworld

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.thoughtworks.androidtrain.helloworld.compose.ComposeViewModel
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.fakedatasource.FakeDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewModuleTest {

    private lateinit var viewModel: ComposeViewModel

    @Before
    fun beforeEach() {
        viewModel = ComposeViewModel(FakeDataSource())
    }

    @Test
    fun testInsertOneTweet() = runBlockingTest {
        val tweet = Tweet("Jack", null, emptyList(), emptyList(), null, null)
        viewModel.insertData(tweet)
        viewModel.fetchTweets()
        delay(1000)
        Assert.assertEquals(1, viewModel.tweets.value.size)
    }
}
