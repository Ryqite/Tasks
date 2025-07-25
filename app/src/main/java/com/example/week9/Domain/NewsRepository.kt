package com.example.week9.Domain

interface NewsRepository {
   suspend fun getLatestNews(): List<News>
}