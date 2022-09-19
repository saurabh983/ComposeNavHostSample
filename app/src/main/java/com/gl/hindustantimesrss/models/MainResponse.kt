package com.gl.hindustantimesrss.models

data class MainResponse(
    var title: String,
    var description: String,
    var url: String,
    var image: String,
    var items: List<NewsItems>
)