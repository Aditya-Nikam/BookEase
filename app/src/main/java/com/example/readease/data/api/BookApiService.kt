package com.example.readease.data.api
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 10
    ): BooksApiResponse
}