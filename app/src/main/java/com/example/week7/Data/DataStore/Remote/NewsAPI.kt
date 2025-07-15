package com.example.week7.Data.DataStore.Remote

import com.example.week7.Data.DTO.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.week7.Domain.Constants.Companion.API_KEY

/**
 * Интерфейс для работы с News API
 * Предоставляет метод [getLatestNews] для получения новостей из внешнего API с:
 * @param page Номер страницы для пагинации
 * @param query Поисковый запрос, по умолчанию "android"
 * @param apikey API-ключ для аутентификации
 * @return Ответ на сетевой запрос в виде объекта [NewsResponse],
 * содержащего список статей
 */

interface NewsAPI {
    @GET("v2/everything")
    suspend fun getLatestNews(
        @Query("page")
        page: Int = 1,
        @Query("q")
        query: String = "android",
        @Query("apiKey")
        apikey: String = API_KEY
    ): NewsResponse
}