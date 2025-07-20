package com.example.week7.Data.DataSource.Remote

import com.example.week7.Data.DTO.NewsResponse

interface RemoteDataSource {
   suspend fun getLatestNews(): NewsResponse
}