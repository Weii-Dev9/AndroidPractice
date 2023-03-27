package com.thoughtworks.androidtrain.helloworld.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.AppDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.CommentDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.ImageDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.SenderDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.TweetDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.CommentEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.ImageEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.TweetEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ExampleInstrumentedTest {
    private lateinit var database: AppDatabase
    private lateinit var tweetDao: TweetDao
    private lateinit var senderDao: SenderDao
    private lateinit var imageDao: ImageDao
    private lateinit var commentDao: CommentDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        tweetDao = database.tweetDao()
        senderDao = database.senderDao()
        imageDao = database.imageDao()
        commentDao = database.commentDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertSenderEntity_test() = runTest {
        val senderEntity = SenderEntity()
        senderEntity.id = 1
        senderEntity.nick = "Weii"
        senderEntity.avatar = "own_pic"
        senderEntity.username = "Weii"

        senderDao.insert(senderEntity)

        Assert.assertEquals(1, senderDao.getAll().size)
    }

    @Test
    fun insertTweetEntity_test() = runTest {
        val senderEntity = SenderEntity(1L, "Weii", "Weii", "own_pic")
        senderDao.insert(senderEntity)

        val tweetEntity = TweetEntity(1L, 1L, "你好")
        tweetDao.insert(tweetEntity)

        val size = tweetDao.getAll().first().size

        Assert.assertEquals(1, size)
    }

    @Test
    fun insertImageEntity_test() = runTest {
        val senderEntity = SenderEntity(1L, "Weii", "Weii", "own_pic")
        senderDao.insert(senderEntity)

        val tweetEntity = TweetEntity(1L, 1L, "你好")
        tweetDao.insert(tweetEntity)

        val imageEntity = ImageEntity(1L, 1L, "test")
        imageDao.insert(imageEntity)

        Assert.assertEquals(1, imageDao.getByTweetId(imageEntity.tweetId).size)
    }

    @Test
    fun insertCommentEntity_test() = runTest {
        val senderEntity = SenderEntity(1L, "Weii", "Weii", "own_pic")
        senderDao.insert(senderEntity)

        val tweetEntity = TweetEntity(1L, 1L, "你好")
        tweetDao.insert(tweetEntity)

        val commentEntity = CommentEntity(1L, 1L, 1L, "test_comment")
        commentDao.insert(commentEntity)

        Assert.assertEquals(1, commentDao.getAll().first().size)
    }

}
