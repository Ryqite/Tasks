package com.example.week7.Domain

interface NewsRepository {
    fun getLatestNews(): List<News>
}