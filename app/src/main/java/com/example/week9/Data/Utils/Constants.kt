package com.example.week9.Data.Utils

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
        const val API_KEY = "7a984841150b48ddb54733f641fc6bc0"
        const val BASE_URL = "https://newsapi.org/"
    }
}