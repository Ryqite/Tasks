package com.example.week9.Data.Mappers

import com.example.week9.Data.DTO.Film
import com.example.week9.Domain.Films

/**
 * Функция для превращения объекта [Article] в объект [Films]
 */
fun Film.toFilms(): Films = Films(
    title = nameEn?: "",
    kinopoiskId = filmId?: 0,
    description = description?: "",
    rating = rating?: "",
    imageURL = posterUrl?: ""
)