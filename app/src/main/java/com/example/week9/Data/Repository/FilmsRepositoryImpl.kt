package com.example.week9.Data.Repository

import com.example.week9.Data.DataSource.Remote.RemoteDataSource
import com.example.week9.Data.Mappers.toFilms
import com.example.week9.Domain.Films
import com.example.week9.Domain.FilmsRepository

class FilmsRepositoryImpl(private val remoteDataSource: RemoteDataSource): FilmsRepository {
    override suspend fun getLatestFilms(): List<Films> {
        val response = remoteDataSource.getLatestFilms()
        val filmsList = response.films.map { it.toFilms() }
        return filmsList
    }
}