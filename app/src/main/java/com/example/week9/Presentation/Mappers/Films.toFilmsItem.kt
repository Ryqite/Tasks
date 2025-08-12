package com.example.week9.Presentation.Mappers

import com.example.week9.Domain.Data.Films
import com.example.week9.Presentation.Data.FilmsItem

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