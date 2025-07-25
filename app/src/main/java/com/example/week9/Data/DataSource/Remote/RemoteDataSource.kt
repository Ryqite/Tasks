package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.NewsResponse

interface RemoteDataSource {
   suspend fun getLatestNews(): NewsResponse
}