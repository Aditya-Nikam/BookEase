package com.example.readease.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    val api: BookApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/") // base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookApiService::class.java)
    }
}