package com.example.week9.Data.Mappers

import com.example.week9.Data.DTO.Article
import com.example.week9.Domain.News

/**
 * Функция для превращения объекта [Article] в объект [News]
 */
fun Article.toNews(): News = News(
    title = title?: "",
    source = url?: "",
    description = description?: "",
    imageURL = urlToImage?: ""
)