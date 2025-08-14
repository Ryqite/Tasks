package com.example.week12.Data.Repository

import com.example.week12.Data.DataSource.Local.ProfileDataSource
import com.example.week12.Data.Mappers.toProfileParametersData
import com.example.week12.Domain.Models.ProfileParametersData
import com.example.week12.Domain.ProfileRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ProfileRepositoryImpl @Inject constructor(private val dataSource: ProfileDataSource): ProfileRepository {
    override fun getProfileData(): ProfileParametersData {
        val response = dataSource.getProfileData()
        return response.toProfileParametersData()
    }
}