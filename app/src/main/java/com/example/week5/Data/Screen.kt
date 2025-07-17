package com.example.week5.Data

import kotlinx.serialization.Serializable

/**
 * Класс содержащий обьекты для навигации в navHost
 */
sealed class Screen {
    @Serializable
    object ProductsScreen: Screen()
    @Serializable
    object BasketScreen: Screen()
    @Serializable
    object ProfileScreen: Screen()
    @Serializable
    data class DetailScreen(val id:Int): Screen()

}
