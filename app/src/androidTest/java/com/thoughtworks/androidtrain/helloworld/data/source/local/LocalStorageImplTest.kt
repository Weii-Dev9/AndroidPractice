@file:OptIn(ExperimentalCoroutinesApi::class)

package com.thoughtworks.androidtrain.helloworld.data.source.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class LocalStorageImplTest {

    private lateinit var context: Context

    @Mock
    private lateinit var storage: LocalStorage

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        storage = LocalStorageImpl(context)
        context.deleteDatabase("practice-db")
    }

    @Test
    fun testGetTweets() = runTest {
        // Given
        val sender = Sender("Weii", "Weii", "test_ava")
        val tweets = listOf(
            Tweet("tweet1", sender, emptyList(), emptyList(), null, null),
            Tweet("tweet2", sender, emptyList(), emptyList(), null, null),
            Tweet("tweet3", sender, emptyList(), emptyList(), null, null)
        )
        storage.updateTweets(tweets)

        // When
        val result = storage.getTweets().first()

        // Then
        assertEquals(tweets.size, result.size)
        tweets.zip(result).forEach { (expected, actual) ->
            assertEquals(expected.content, actual.content)
        }
    }

    @Test
    fun testInsertTweet() = runTest {
        // Given
        val sender = Sender("Weii", "Weii", "test_ava")
        val tweet = Tweet("tweet1", sender, emptyList(), emptyList(), null, null)


        // When
        val tweetId = storage.insertTweet(tweet)
        val result = storage.getTweets().first()

        // Then
        assertTrue(tweetId > 0)
        assertEquals(1, result.size)
        assertEquals(tweet.content, result[0].content)
    }
}

