package com.example.week12.Presentation.Mappers

import com.example.week12.Domain.Models.ProfileParametersData
import com.example.week12.Presentation.Models.ProfileData

fun ProfileParametersData.toProfileData(): ProfileData = ProfileData(
    img = img,
    nickName = nickName,
    fullName = fullName
)
