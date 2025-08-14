package com.example.week12.Data.DataSource.Local

import com.example.week12.Data.Entity.Profile

interface ProfileDataSource {
    fun getProfileData(): Profile
}