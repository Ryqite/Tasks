package com.example.week12.Data.DataSource.Local

import com.example.week12.Data.Entity.Profile
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ProfileDataSourceImpl @Inject constructor(private val data: Profile): ProfileDataSource {
    override fun getProfileData(): Profile {
        return data
    }
}