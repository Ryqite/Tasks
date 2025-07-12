package com.example.week7.Navigation

import kotlinx.serialization.Serializable
sealed class NavigationScreens {
    @Serializable
    object MainScreen
    @Serializable
    data class DetailScreen(val id:Int)
}