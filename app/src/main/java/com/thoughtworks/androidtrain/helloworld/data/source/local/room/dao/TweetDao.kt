@file:Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS")

package com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.TweetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TweetDao {
    @Query("SELECT * FROM tweet")
    fun getAll(): Flow<List<TweetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tweetEntity: TweetEntity): Long

    @Query("SELECT * FROM tweet WHERE id = :tweetId")
    suspend fun getTweetById(tweetId: Long): TweetEntity
}
