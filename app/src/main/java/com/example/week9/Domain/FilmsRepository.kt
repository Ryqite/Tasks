package com.example.week9.Domain

import com.example.week9.Domain.Data.Films

interface FilmsRepository {
   suspend fun getLatestFilms(keyword: String): List<Films>
}