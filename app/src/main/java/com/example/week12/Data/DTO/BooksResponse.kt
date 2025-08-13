package com.example.week12.Data.DTO

data class BooksResponse(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)