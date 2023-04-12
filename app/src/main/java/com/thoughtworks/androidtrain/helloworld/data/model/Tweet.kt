package com.thoughtworks.androidtrain.helloworld.data.model

import java.util.UUID


data class Tweet(
    var id: String? = UUID.randomUUID().toString(),
    var content: String = "",
    var sender: Sender? = null,
    var images: List<Image> = emptyList(),
    val comments: List<Comment> = emptyList(),
)


