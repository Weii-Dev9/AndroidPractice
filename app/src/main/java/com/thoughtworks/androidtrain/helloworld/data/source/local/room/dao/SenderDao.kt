@file:Suppress("UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS")

package com.thoughtworks.androidtrain.helloworld.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity

@Dao
interface SenderDao {
    @Query("SELECT * FROM sender")
    fun getAll(): List<SenderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(senderEntity: SenderEntity): Long
}
