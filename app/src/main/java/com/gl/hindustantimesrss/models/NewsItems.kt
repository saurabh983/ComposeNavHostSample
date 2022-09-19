package com.gl.hindustantimesrss.models

import com.google.gson.annotations.SerializedName

data class NewsItems(var title: String,
                     var description: String,
                     var url: String,
                     var link: String,
                     var media: Media
)


data class Media(
    @SerializedName("content")
    var content: List<Content>
)

data class Content(
    @SerializedName("url")
    var url: List<String>,
)