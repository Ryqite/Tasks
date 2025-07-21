package com.example.week7.Presentation

import com.example.week7.Data.DTO.Article
import com.example.week7.Domain.News
/**
 * Функция для превращения объекта [News] в объект [NewsItem]
 */
fun News.toNewsItem(): NewsItem = NewsItem(
    title = title,
    imageURL = imageURL,
    source = source,
    description = description
)