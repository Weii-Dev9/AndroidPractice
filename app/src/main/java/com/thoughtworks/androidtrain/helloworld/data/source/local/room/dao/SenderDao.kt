@file:Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS")

package com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao

import androidx.room.*
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity

@Dao
interface SenderDao {
    @Query("SELECT * FROM sender")
    suspend fun getAll(): List<SenderEntity>

    @Query("SELECT * FROM sender WHERE id = :id")
    suspend fun getById(id: String): SenderEntity

    @Query("SELECT isUser FROM sender WHERE username= :username")
    suspend fun getByUsername(username: String?): Boolean

    @Query("SELECT * FROM sender WHERE isUser= :isUser")
    suspend fun getByIsUser(isUser: Int): SenderEntity

    @Query("DELETE FROM sender WHERE isUser = 1")
    suspend fun delete()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(senderEntity: SenderEntity)
}
