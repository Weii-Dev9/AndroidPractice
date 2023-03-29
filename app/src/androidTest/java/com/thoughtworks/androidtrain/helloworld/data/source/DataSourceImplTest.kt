package com.thoughtworks.androidtrain.helloworld.data.source

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataSourceImplTest {
    private lateinit var context: Context

    private lateinit var dataSource: DataSource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        dataSource = DataSourceImpl(context)
        context.deleteDatabase("practice-db")
    }

    @Test
    fun testFetchTweets() = runTest {
        // Given
        val sender = Sender("Weii", "Weii", "test_ava")
        val tweets = listOf(
            Tweet("tweet1", sender, emptyList(), emptyList(), null, null),
            Tweet("", sender, emptyList(), emptyList(), null, null),
            Tweet("tweet2", sender, emptyList(), emptyList(), null, null),
            Tweet("", sender, emptyList(), emptyList(), null, null),
            Tweet("tweet3", sender, emptyList(), emptyList(), null, null)
        )
        dataSource.insertTweet(tweets[0])
        dataSource.insertTweet(tweets[1])
        dataSource.insertTweet(tweets[2])
        dataSource.insertTweet(tweets[3])
        dataSource.insertTweet(tweets[4])


        // When
        val result = dataSource.fetchTweets().first()

        // Then
        assertEquals(3, result.size)
        assertEquals("tweet1", result[0].content)
        assertEquals("tweet2", result[1].content)
        assertEquals("tweet3", result[2].content)
    }

    @Test
    fun testInsertTweet() = runTest {
        // Given
        val sender = Sender("Weii", "Weii", "test_ava")
        val tweet = Tweet("tweet1", sender, emptyList(), emptyList(), null, null)

        // When
        dataSource.insertTweet(tweet)
        val result = dataSource.fetchTweets().first()

        // Then
        assertEquals(1, result.size)
        assertEquals("tweet1", result[0].content)
    }
}
