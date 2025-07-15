package com.example.week7.Data.DataStore.Remote

import com.example.week7.Data.DTO.NewsResponse

class RemoteDataSourceImpl(private val newsAPI: NewsAPI): RemoteDataSource {
    override suspend fun getLatestNews(): NewsResponse {
        return newsAPI.getLatestNews()
    }
}