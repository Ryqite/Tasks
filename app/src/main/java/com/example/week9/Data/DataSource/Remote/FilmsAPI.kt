package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.week9.Data.Utils.Constants.Companion.API_KEY
import retrofit2.http.Header

/**
 * Интерфейс для работы с films API
 * Предоставляет метод [getLatestFilms] для получения новостей из внешнего API с:
 * @param page Номер страницы для пагинации
 * @param query Поисковый запрос, по умолчанию "android"
 * @param apikey API-ключ для аутентификации
 * @return Ответ на сетевой запрос в виде объекта [filmsResponse],
 * содержащего список статей
 */

interface FilmsAPI {
    @GET("api/v2.1/films/search-by-keyword")
    suspend fun getLatestFilms(
        @Header("X-API-KEY")
        apikey: String = API_KEY,
        @Query("keyword")
        keyword: String = "f",
        @Query("page")
        page: Int = 1,
    ): FilmResponse
}