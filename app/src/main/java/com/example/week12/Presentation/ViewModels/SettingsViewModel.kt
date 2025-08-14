package com.example.week12.Presentation.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import com.example.week12.Domain.UseCases.GetLanguageUseCase
import com.example.week12.Domain.UseCases.SaveLanguageUseCase
import com.example.week12.Domain.UseCases.SetAppLocaleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val changeDarkThemeUseCase: ChangeDarkThemeUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setAppLocaleUseCase: SetAppLocaleUseCase
): ViewModel() {

    val themeState: Flow<AppTheme> = changeDarkThemeUseCase.themeState

    fun changeAppTheme(){
        viewModelScope.launch {
            changeDarkThemeUseCase()
        }
    }

    fun saveLanguage(language: String){
        saveLanguageUseCase(language)
    }
    fun getLanguage(): String{
        return getLanguageUseCase()
    }

    fun setAppLocale(context: Context,language: String): Context {
        return setAppLocaleUseCase(context,language)
    }

}