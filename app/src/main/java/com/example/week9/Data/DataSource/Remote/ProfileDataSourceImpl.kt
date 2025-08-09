package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.Profile
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(private val data: Profile): ProfileDataSource {
    override fun getProfileData(): Profile {
        return data
    }
}