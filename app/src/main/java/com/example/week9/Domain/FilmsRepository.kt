package com.example.week9.Domain

interface FilmsRepository {
   suspend fun getLatestFilms(): List<Films>
}