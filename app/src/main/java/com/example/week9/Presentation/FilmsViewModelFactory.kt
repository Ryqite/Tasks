//package com.example.week9.Presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.week9.Domain.GetLatestFilmsUseCase
//import javax.inject.Inject
//
//class FilmsViewModelFactory @Inject constructor(
//    private val getLatestFilmsUseCase: GetLatestFilmsUseCase
//):ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(FilmsViewModel::class.java)){
//            return FilmsViewModel(
//                getLatestFilms = getLatestFilmsUseCase
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}