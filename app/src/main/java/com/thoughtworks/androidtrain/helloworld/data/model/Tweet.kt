package com.thoughtworks.androidtrain.helloworld.data.model

import com.google.gson.annotations.SerializedName

data class Tweet(
    var content: String,
    var sender: Sender? = null,
    var images: List<Image>,
    var comments: List<Comment>,
    var error: String?,
    @SerializedName("unknown error")
    var unknownError: String?
) {

    constructor() : this("", null, emptyList(), emptyList(), null, null)
}


