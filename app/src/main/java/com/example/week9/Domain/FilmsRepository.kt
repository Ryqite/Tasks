package com.example.week9.Domain

import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
   suspend fun getLatestFilms(keyword: String): List<Films>
}