package com.example.week7.Presentation

import com.example.week7.Domain.News

fun News.toNewsItem(): NewsItem = NewsItem(
    title = title,
    imageURL = imageURL,
    source = source,
    description = description
)