package com.example.week7.Mappers

import com.example.week7.Data.Article
import com.example.week7.Data.News

/**
 * Функция для превращения объекта [Article] в объект [News]
 */
fun Article.toNews(): News = News(
    title = title?: "",
    source = url?: "",
    description = description?: "",
    imageURL = urlToImage?: ""
)