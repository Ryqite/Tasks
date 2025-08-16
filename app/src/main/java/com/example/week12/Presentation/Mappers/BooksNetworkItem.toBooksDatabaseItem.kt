package com.example.week12.Presentation.Mappers

import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.Models.BooksNetworkItem

fun BooksNetworkItem.toBooksDatabaseItem(): BooksDatabaseItem = BooksDatabaseItem(
    id = 0,
    title = title,
    description = description,
    rating = rating,
    publishedAt = publishedAt,
    isSaved = true
)