package com.example.week12.Domain.Models

data class BooksFromDatabase(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val rating: Int = 0,
    val publishedAt: String = "",
    val isSaved: Boolean = false
)