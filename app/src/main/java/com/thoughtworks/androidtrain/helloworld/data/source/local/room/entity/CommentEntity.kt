package com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//两个外键【sender、tweet】
@Entity(
    tableName = "comment",
    foreignKeys = [ForeignKey(
        entity = TweetEntity::class,
        parentColumns = ["id"],
        childColumns = ["tweet_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = SenderEntity::class,
        parentColumns = ["id"],
        childColumns = ["sender_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CommentEntity(
    @PrimaryKey
    var commentId: String,

    @ColumnInfo(name = "tweet_id", index = true)
    var tweetId: String,

    @ColumnInfo(name = "sender_id", index = true)
    var senderId: String,

    @ColumnInfo(name = "content")
    var content: String? = null
)
