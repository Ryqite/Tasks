package com.example.week12.Data.DataSource.Remote

import com.example.week12.Data.Entity.DTO.BooksResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val booksAPI: BooksAPI): RemoteDataSource {
    override suspend fun getBooksBySearch(keyword: String): BooksResponse {
       return booksAPI.getBooksBySearch(keywords = keyword)
    }
}