package com.example.week12.Presentation.Mappers

import com.example.week12.Domain.Models.Books
import com.example.week12.Presentation.Models.BooksItem

fun Books.toBooksItem(): BooksItem = BooksItem(
    title = title,
    description = description,
    image = image,
    rating = rating,
    publishedAt = publishedAt
)