package com.example.week12.Domain.UseCases

import com.example.week12.Domain.Models.ProfileParametersData
import com.example.week12.Domain.ProfileRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class GetProfileDataUseCase @Inject constructor(private val repositoty: ProfileRepository) {
    operator fun invoke(): ProfileParametersData {
        return repositoty.getProfileData()
    }
}