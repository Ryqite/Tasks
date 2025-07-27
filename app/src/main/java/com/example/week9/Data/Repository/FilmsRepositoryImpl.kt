package com.example.week9.Data.Repository

import android.util.Log
import com.example.week9.Data.DataSource.Remote.RemoteDataSource
import com.example.week9.Data.Mappers.toFilms
import com.example.week9.Domain.Films
import com.example.week9.Domain.FilmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class FilmsRepositoryImpl(private val remoteDataSource: RemoteDataSource): FilmsRepository {
    override suspend fun getLatestFilms(keyword: String): List<Films> {
        val response = remoteDataSource.getLatestFilms(keyword)
        return response.films.map { it.toFilms() }
    }
}