package com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sender")
data class SenderEntity(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "nick")
    var nick: String,

    @ColumnInfo(name = "avatar")
    var avatar: String,

    @ColumnInfo(name = "isUser")
    var isUser: Boolean = false
)
