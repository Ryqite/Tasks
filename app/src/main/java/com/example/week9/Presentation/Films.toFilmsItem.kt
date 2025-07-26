package com.example.week9.Presentation

import com.example.week9.Domain.Films
/**
 * Функция для превращения объекта [Films] в объект [FilmsItem]
 */
fun Films.toFilmsItem(): FilmsItem = FilmsItem(
    title = title,
    imageURL = imageURL,
    description = description,
    rating = rating,
    kinopoiskId = kinopoiskId
)