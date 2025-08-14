package com.example.week12.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val changeDarkThemeUseCase: ChangeDarkThemeUseCase
): ViewModel() {

    val themeState: Flow<AppTheme> = changeDarkThemeUseCase.themeState

    fun changeAppTheme(){
        viewModelScope.launch {
            changeDarkThemeUseCase()
        }
    }
}