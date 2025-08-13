package com.example.week12.Data.Repository

import com.example.week12.Data.DataSource.Remote.RemoteDataSource
import com.example.week12.Data.Mappers.toBooksFromNetwork
import com.example.week12.Domain.BooksNetworkRepository
import com.example.week12.Domain.Models.BooksFromNetwork
import javax.inject.Inject

class BooksNetworkRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): BooksNetworkRepository {
    override suspend fun getBooksBySearch(keyword: String): List<BooksFromNetwork> {
        val response = remoteDataSource.getBooksBySearch(keyword)
        return response.items.map {item->
            item.volumeInfo.toBooksFromNetwork()
        }
    }
}