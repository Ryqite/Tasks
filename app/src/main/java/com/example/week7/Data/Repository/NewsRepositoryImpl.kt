package com.example.week7.Data.Repository

import com.example.week7.Data.DataStore.Remote.RemoteDataSource
import com.example.week7.Data.Mappers.toNews
import com.example.week7.Domain.News
import com.example.week7.Domain.NewsRepository

class NewsRepositoryImpl(private val remoteDataSource: RemoteDataSource): NewsRepository {
    override suspend fun getLatestNews(): List<News> {
        val response = remoteDataSource.getLatestNews()
        val newsList = response.articles.map { it.toNews() }
        return newsList
    }
}