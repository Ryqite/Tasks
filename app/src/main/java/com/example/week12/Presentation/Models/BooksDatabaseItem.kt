package com.example.week12.Presentation.Models

data class BooksDatabaseItem(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val rating: Double = 0.0,
    val publishedAt: String = "",
    val isSaved: Boolean = false
)
