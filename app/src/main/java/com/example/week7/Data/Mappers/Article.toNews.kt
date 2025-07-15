package com.example.week7.Data.Mappers

import com.example.week7.Data.DTO.Article
import com.example.week7.Domain.News

/**
 * Функция для превращения объекта [Article] в объект [News]
 */
fun Article.toNews(): News = News(
    title = title?: "",
    source = url?: "",
    description = description?: "",
    imageURL = urlToImage?: ""
)