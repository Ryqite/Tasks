package com.example.week12.Data.Mappers

import com.example.week12.Data.Entity.Profile
import com.example.week12.Domain.Models.ProfileParametersData

/**
 * Функция для превращения объекта [Article] в объект [Films]
 */
fun Profile.toProfileParametersData(): ProfileParametersData = ProfileParametersData(
    img = img,
    nickName = nickName,
    fullName = fullName,
)