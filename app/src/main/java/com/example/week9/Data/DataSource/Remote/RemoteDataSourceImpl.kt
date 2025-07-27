package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.FilmResponse

class RemoteDataSourceImpl(private val filmsAPI: FilmsAPI): RemoteDataSource {
    override suspend fun getLatestFilms(keyword: String): FilmResponse {
        return filmsAPI.getLatestFilms(keyword = keyword)
    }
}