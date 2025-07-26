package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.FilmResponse

interface RemoteDataSource {
   suspend fun getLatestFilms(): FilmResponse
}