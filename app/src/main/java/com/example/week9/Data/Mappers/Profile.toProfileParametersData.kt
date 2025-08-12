package com.example.week9.Data.Mappers

import com.example.week9.Data.DTO.Film
import com.example.week9.Data.DTO.Profile
import com.example.week9.Domain.Data.Films
import com.example.week9.Domain.Data.ProfileParametersData

/**
 * Функция для превращения объекта [Article] в объект [Films]
 */
fun Profile.toProfileParametersData(): ProfileParametersData = ProfileParametersData(
    img = img,
    nickName = nickName,
    fullName = fullName,
)