package com.example.week12.Data.Repository

import com.example.week12.Data.DataSource.Remote.RemoteDataSource
import com.example.week12.Data.Mappers.toBooks
import com.example.week12.Domain.BooksRepository
import com.example.week12.Domain.Models.Books
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): BooksRepository {
    override suspend fun getBooksBySearch(keyword: String): List<Books> {
        val response = remoteDataSource.getBooksBySearch(keyword)
        return response.items.map {item->
            item.volumeInfo.toBooks()
        }
    }
}