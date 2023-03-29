@file:Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS")

package com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * FROM comment")
    fun getAll(): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commentEntity: CommentEntity)
}
