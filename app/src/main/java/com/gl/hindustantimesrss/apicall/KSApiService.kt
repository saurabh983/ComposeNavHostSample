package com.gl.hindustantimesrss.apicall

import com.gl.hindustantimesrss.models.MainResponse
import retrofit2.http.*

interface KSApiService {

    @GET("?u=https://www.hindustantimes.com/feeds/rss/india-news/rssfeed.xml")
    suspend fun getRssFeeds(): MainResponse
}