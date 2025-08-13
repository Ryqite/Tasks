package com.example.week12.Presentation.Utils

import kotlinx.serialization.Serializable

sealed class NavigationScreens {
    @Serializable
    object MainScreen
    @Serializable
    data class DetailScreen(val id:String)
}