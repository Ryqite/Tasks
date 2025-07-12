package com.example.week7.Network

import com.example.week7.Data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.week7.Util.Constants.Companion.API_KEY
import retrofit2.Response

interface NewsAPI {
    @GET("api/1/latest?")
    suspend fun getLatestNews(
        @Query("apikey")
        apikey: String = API_KEY
    ): NewsResponse
}