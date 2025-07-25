package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.NewsResponse

class RemoteDataSourceImpl(private val newsAPI: NewsAPI): RemoteDataSource {
    override suspend fun getLatestNews(): NewsResponse {
        return newsAPI.getLatestNews()
    }
}