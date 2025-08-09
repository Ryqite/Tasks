package com.example.week9.Data.Repository

import com.example.week9.Data.DTO.Profile
import com.example.week9.Data.DataSource.Remote.ProfileDataSource
import com.example.week9.Data.Mappers.toProfileParametersData
import com.example.week9.Domain.Data.ProfileParametersData
import com.example.week9.Domain.ProfileParameters
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
@ActivityRetainedScoped
class ProfileParametersImpl @Inject constructor(private val dataSource: ProfileDataSource): ProfileParameters {
    override fun getProfileData(): ProfileParametersData {
        val response = dataSource.getProfileData()
        return response.toProfileParametersData()
    }
}