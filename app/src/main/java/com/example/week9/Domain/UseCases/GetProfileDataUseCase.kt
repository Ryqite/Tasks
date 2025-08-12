package com.example.week9.Domain.UseCases

import com.example.week9.Domain.Data.ProfileParametersData
import com.example.week9.Domain.ProfileParameters
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
@ActivityRetainedScoped
class GetProfileDataUseCase @Inject constructor(private val repositoty: ProfileParameters) {
    operator fun invoke(): ProfileParametersData {
       return repositoty.getProfileData()
    }
}