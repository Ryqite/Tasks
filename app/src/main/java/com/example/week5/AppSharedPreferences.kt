package com.example.week5

import android.content.Context
import java.util.Locale

/**
 * Класс для управления настройками приложения, хранящимися в SharedPreferences.
 *
 * @property context Контекст приложения
 */
class AppSharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_settings",Context.MODE_PRIVATE)
    fun saveLanguage(language: String){
        sharedPreferences.edit().putString("app_language",language).apply()
    }
    fun getLanguage():String{
        return sharedPreferences.getString("app_language",Locale.getDefault().language)?: "en"
    }
}