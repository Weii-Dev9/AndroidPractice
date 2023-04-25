package com.thoughtworks.androidtrain.helloworld.data.model

import java.util.*

data class Sender(
    var id: String? = UUID.randomUUID().toString(),
    var isCurrentUser: Boolean = false,
    var username: String = "",
    val nick: String = "",
    val avatar: String = ""
)
