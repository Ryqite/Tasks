package com.example.week9.Data.DTO

data class FilmResponse(
    val films: List<Film>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
)