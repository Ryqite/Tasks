package com.example.week7.Network

import com.example.week7.Util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Класс для работы с Retrofit.
 * Содержит настройки и экземпляр Retrofit и API-клиента
 */
class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            val client = OkHttpClient.Builder()
                .addInterceptor(RetryInterceptor(maxRetries = 3))
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}