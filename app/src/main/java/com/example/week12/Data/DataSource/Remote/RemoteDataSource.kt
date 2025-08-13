package com.example.week12.Data.DataSource.Remote

import com.example.week12.Data.Entity.DTO.BooksResponse

interface RemoteDataSource {
    suspend fun getBooksBySearch(keyword: String): BooksResponse
}