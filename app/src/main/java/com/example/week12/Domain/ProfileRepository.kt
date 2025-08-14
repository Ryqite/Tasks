package com.example.week12.Domain

import com.example.week12.Domain.Models.ProfileParametersData

interface ProfileRepository {
    fun getProfileData(): ProfileParametersData
}