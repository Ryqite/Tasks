package com.example.week12.Data.Mappers

import com.example.week12.Data.Entity.DBO.Profile
import com.example.week12.Domain.Models.ProfileParametersData

fun ProfileParametersData.toProfile(): Profile = Profile(
    img = img,
    nickName = nickName,
    fullName = fullName,
    password = password
)