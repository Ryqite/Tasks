package com.example.week12.Presentation.Mappers

import com.example.week12.Domain.Models.BooksFromDatabase
import com.example.week12.Presentation.Models.BooksDatabaseItem

fun BooksFromDatabase.toBooksDatabaseItem(): BooksDatabaseItem = BooksDatabaseItem(
    id = id,
    title = title,
    description = description,
    image = image,
    rating = rating,
    publishedAt = publishedAt,
    isSaved = isSaved
)