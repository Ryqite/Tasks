package com.example.week9.Data.Repository

import com.example.week9.Data.DataSource.Remote.RemoteDataSource
import com.example.week9.Data.Mappers.toFilms
import com.example.week9.Domain.Data.Films
import com.example.week9.Domain.FilmsRepository
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): FilmsRepository {
    override suspend fun getLatestFilms(keyword: String): List<Films> {
        val response = remoteDataSource.getLatestFilms(keyword)
        return response.films.map { it.toFilms() }
    }
}