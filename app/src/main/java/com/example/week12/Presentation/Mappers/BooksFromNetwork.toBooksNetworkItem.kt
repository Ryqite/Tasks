package com.example.week12.Presentation.Mappers

import com.example.week12.Domain.Models.BooksFromNetwork
import com.example.week12.Presentation.Models.BooksNetworkItem

fun BooksFromNetwork.toBooksNetworkItem(): BooksNetworkItem = BooksNetworkItem(
    title = title,
    description = description,
    image = image,
    rating = rating,
    publishedAt = publishedAt
)