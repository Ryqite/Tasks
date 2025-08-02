package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.FilmResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val filmsAPI: FilmsAPI): RemoteDataSource {
    override suspend fun getLatestFilms(keyword: String): FilmResponse {
        return filmsAPI.getLatestFilms(keyword = keyword)
    }
}