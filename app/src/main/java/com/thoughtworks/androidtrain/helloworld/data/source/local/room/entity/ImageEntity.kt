package com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "image",
    foreignKeys = [ForeignKey(
        entity = TweetEntity::class,
        parentColumns = ["id"],
        childColumns = ["tweet_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ImageEntity(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "tweet_id", index = true)
    var tweetId: String,

    @ColumnInfo(name = "url")
    var url: String
)
