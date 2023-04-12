package com.thoughtworks.androidtrain.helloworld.data.model

import java.util.*

data class Image(
    var id: String? = UUID.randomUUID().toString(),
    val url: String
)
