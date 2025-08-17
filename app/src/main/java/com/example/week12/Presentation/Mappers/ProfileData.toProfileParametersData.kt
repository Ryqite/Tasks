package com.example.week12.Presentation.Mappers

import com.example.week12.Domain.Models.ProfileParametersData
import com.example.week12.Presentation.Models.ProfileData

fun ProfileData.toProfileParametersData(): ProfileParametersData = ProfileParametersData(
    img = img,
    nickName = nickName,
    fullName = fullName,
    password = password
)
