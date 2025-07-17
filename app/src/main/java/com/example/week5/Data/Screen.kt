package com.example.week5.Data

import kotlinx.serialization.Serializable

/**
 * Класс содержащий обьекты для навигации в navHost
 */
sealed class Screen {
    @Serializable
    object ProductsScreen
    @Serializable
    object BasketScreen
    @Serializable
    object ProfileScreen
    @Serializable
    data class DetailScreen(val id:Int)

}