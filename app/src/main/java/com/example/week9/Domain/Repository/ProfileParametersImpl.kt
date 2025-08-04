package com.example.week9.Domain.Repository

import com.example.week9.Domain.ProfileParameters
import com.example.week9.Domain.Data.ProfileParametersData
import javax.inject.Inject

class ProfileParametersImpl @Inject constructor(private val data: ProfileParametersData): ProfileParameters {
    override fun getProfileData(): ProfileParametersData {
        return data
    }
}