package com.thoughtworks.androidtrain.helloworld.data.model

import java.util.*

data class Comment(
    var id: String? = UUID.randomUUID().toString(),
    val content: String,
    val sender: Sender
)
