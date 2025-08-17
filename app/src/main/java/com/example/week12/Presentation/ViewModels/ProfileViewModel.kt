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
        img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRS0IQhVr9DDJCq61QX28zCoiqDrvezBh5ylw&s",
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