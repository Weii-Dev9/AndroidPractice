package com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//content:tweet表
//sender:在sender表,tweetId为外键【一对一】
//images:在image表，tweetId为外键【一对多】
//comments:在comment表，tweetId为外键【一堆多】
@Entity(
    tableName = "tweet",
    foreignKeys = [ForeignKey(
        entity = SenderEntity::class,
        parentColumns = ["id"],
        childColumns = ["sender_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TweetEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "sender_id", index = true)
    var senderId: Long = 0,

    @ColumnInfo(name = "content")
    var content: String? = null
)
