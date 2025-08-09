package com.example.week9.Data.DataSource.Remote

import com.example.week9.Data.DTO.FilmResponse
import com.example.week9.Data.DTO.Profile

interface ProfileDataSource {
     fun getProfileData(): Profile
}