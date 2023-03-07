package com.thoughtworks.androidtrain.helloworld.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.CommentDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.ImageDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.SenderDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao.TweetDao
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.CommentEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.ImageEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.TweetEntity

@Database(
    entities = [TweetEntity::class, SenderEntity::class, ImageEntity::class, CommentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tweetDao(): TweetDao
    abstract fun senderDao(): SenderDao
    abstract fun imageDao(): ImageDao
    abstract fun commentDao(): CommentDao
}
