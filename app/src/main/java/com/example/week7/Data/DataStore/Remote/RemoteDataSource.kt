package com.example.week7.Data.DataStore.Remote

import com.example.week7.Data.DTO.NewsResponse

interface RemoteDataSource {
   suspend fun getLatestNews(): NewsResponse
}