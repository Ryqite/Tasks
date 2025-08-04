package com.example.week9.Domain.UseCases

import com.example.week9.Domain.Data.ProfileParametersData
import com.example.week9.Domain.ProfileParameters
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(private val repositoty: ProfileParameters) {
    operator fun invoke(): ProfileParametersData {
       return repositoty.getProfileData()
    }
}