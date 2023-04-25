package com.thoughtworks.androidtrain.helloworld.data.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class Tweet(
    var id: String? = UUID.randomUUID().toString(),
    var content: String = "",
    var sender: Sender? = null,
    var images: List<Image> = emptyList(),
    val comments: List<Comment> = emptyList(),
    var error: String? = "",
    @SerializedName("unknown error") var unknownError: String? = ""
)


