package com.thoughtworks.androidtrain.helloworld.data.source

import com.thoughtworks.androidtrain.helloworld.data.model.Comment
import com.thoughtworks.androidtrain.helloworld.data.model.Image
import com.thoughtworks.androidtrain.helloworld.data.model.Sender
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.CommentEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.ImageEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.SenderEntity
import com.thoughtworks.androidtrain.helloworld.data.source.local.room.entity.TweetEntity
import java.util.*

fun TweetEntity.toTweet() = Tweet(
    id = id,
    content = content
)

fun SenderEntity.toSender() = Sender(
    id = id,
    username = username,
    nick = nick,
    avatar = avatar,
    isCurrentUser = isCurrentUser
)

fun ImageEntity.toImage() = Image(
    id = id,
    url = url
)

fun Tweet.toEntity(): TweetEntity {
    id = id ?: UUID.randomUUID().toString()
    return TweetEntity(
        id = id!!,
        senderId = sender?.toEntity()?.id ?: "",
        content = content
    )
}

fun Sender.toEntity(): SenderEntity {
    id = id ?: UUID.randomUUID().toString()
    return SenderEntity(
        id = id!!,
        username = username,
        nick = nick,
        avatar = avatar,
        isCurrentUser = isCurrentUser
    )
}

fun Image.toEntity(tweetId: String?): ImageEntity {
    id = id ?: UUID.randomUUID().toString()
    return ImageEntity(
        id = id!!,
        tweetId = tweetId ?: UUID.randomUUID().toString(),
        url = url
    )
}

fun Comment.toEntity(tweetId: String): CommentEntity {
    id = id ?: UUID.randomUUID().toString()
    return CommentEntity(
        commentId = id!!,
        senderId = sender.toEntity().id,
        tweetId = tweetId,
        content = content
    )
}
