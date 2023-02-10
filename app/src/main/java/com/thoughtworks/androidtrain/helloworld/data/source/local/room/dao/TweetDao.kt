@file:Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS")

package com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.TweetEntity
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


@Dao
interface TweetDao {
    @Query("SELECT * FROM tweet")
    fun getAll(): Flowable<List<TweetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tweetEntity: TweetEntity): Single<Long>
}