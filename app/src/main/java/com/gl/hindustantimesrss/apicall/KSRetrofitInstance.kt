package com.gl.hindustantimesrss.apicall

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object KSRetrofitInstance {

    fun provideApi(): KSApiService {
        return Retrofit.Builder()
            .baseUrl("https://send-rss-get-json.herokuapp.com/convert/")
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KSApiService::class.java)
    }

    private fun getOkHttpClient() = OkHttpClient().newBuilder()
//        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .build()
}