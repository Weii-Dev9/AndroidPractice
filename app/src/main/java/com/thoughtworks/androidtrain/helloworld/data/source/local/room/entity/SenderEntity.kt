package com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sender")
class SenderEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "username")
    var username: String? = null

    @ColumnInfo(name = "nick")
    var nick: String? = null

    @ColumnInfo(name = "avatar")
    var avatar: String? = null
}