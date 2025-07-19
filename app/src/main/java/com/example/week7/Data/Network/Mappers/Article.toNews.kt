package com.example.week7.Data.Network.Mappers

import com.example.week7.Domain.Article
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