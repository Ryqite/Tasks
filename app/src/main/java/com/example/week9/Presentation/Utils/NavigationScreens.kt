package com.example.week9.Presentation.Utils

import kotlinx.serialization.Serializable

/**
 * Класс объектов для навигации в NavHost
 *
 * @property MainScreen представляет точку навигации Главный экран
 * @property ProfileScreen представляет точку навигации Экран профиля
 * @property DetailScreen представляет точку навигации Экран деталей
 * с @param [title] в виде идентефикатора для дальнейших сравнений
 */
sealed class NavigationScreens {
    @Serializable
    object MainScreen
    @Serializable
    object ProfileScreen
    @Serializable
    data class DetailScreen(val id:Int)
}