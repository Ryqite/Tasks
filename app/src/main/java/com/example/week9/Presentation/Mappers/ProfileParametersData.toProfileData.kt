package com.example.week9.Presentation.Mappers

import com.example.week9.Domain.Data.ProfileParametersData
import com.example.week9.Presentation.Data.ProfileData

fun ProfileParametersData.toProfileData(): ProfileData = ProfileData(
    img = img,
    nickName = nickName,
    fullName = fullName
)
