package com.thoughtworks.androidtrain.helloworld.data.model

import com.google.gson.annotations.SerializedName

data class Tweet(
    val content: String?,
    val sender: Sender?,
    val images: List<Image>?,
    val comments: List<Comment>?,
    val error: String?,
    @SerializedName("unknown error")
    val unknownError: String?
)
