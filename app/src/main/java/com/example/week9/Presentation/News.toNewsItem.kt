package com.example.week9.Presentation

import com.example.week9.Data.DTO.Article
import com.example.week9.Domain.News
/**
 * Функция для превращения объекта [News] в объект [NewsItem]
 */
fun News.toNewsItem(): NewsItem = NewsItem(
    title = title,
    imageURL = imageURL,
    source = source,
    description = description
)