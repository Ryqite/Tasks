package com.example.week12.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import com.example.week12.Domain.UseCases.GetProfileDataUseCase
import com.example.week12.Presentation.Mappers.toProfileData
import com.example.week12.Presentation.Models.ProfileData
import com.example.week12.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {
    private val _profileData = MutableStateFlow(ProfileData(
        img = R.drawable.ic_launcher_foreground,
        nickName = "",
        fullName = ""
    )
    )
    val profileData: StateFlow<ProfileData> = _profileData
    init {
        getProfileData()
    }
    fun getProfileData(){
        _profileData.value = getProfileDataUseCase().toProfileData()
    }
}