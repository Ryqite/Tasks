package com.example.week12.Data.DataSource.Remote

import com.example.week12.Data.DTO.BooksResponse
import com.example.week12.Data.Utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksAPI {
    @GET("books/v1/volumes")
    suspend fun getBooksBySearch(
        @Query("key")
        apiKey: String = API_KEY,
        @Query("q")
        keywords: String = "Romantic",
        @Query("page")
        page: Int = 1
    ): BooksResponse
}