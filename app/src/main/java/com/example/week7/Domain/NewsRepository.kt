package com.example.week7.Domain

interface NewsRepository {
   suspend fun getLatestNews(): List<News>
}