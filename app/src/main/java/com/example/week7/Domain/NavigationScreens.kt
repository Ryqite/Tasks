package com.example.week7.Domain

import kotlinx.serialization.Serializable

/**
 * Класс объектов для навигации в NavHost
 *
 * @property MainScreen представляет точку навигации Главный экран
 * @property DetailScreen представляет точку навигации Экран деталей
 * с @param [title] в виде идентефикатора для дальнейших сравнений
 */
sealed class NavigationScreens {
    @Serializable
    object MainScreen
    @Serializable
    data class DetailScreen(val title:String)
}