package com.example.week7.Data.Utils

import com.example.week7.BuildConfig

/**
 * Класс содержащий все константы
 *
 * Все константы объявлены в [companion object] для
 * глобального доступа без создания экземпляра класса.
 *
 * @property [API_KEY] API-ключ, берется из BuildConfig
 * @property [BASE_URL] Базовый URL для доступа к NewsAPI
 */
class Constants {
    companion object{
        const val API_KEY =  BuildConfig.API_KEY
        const val BASE_URL = "https://newsapi.org/"
    }
}