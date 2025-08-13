package com.example.week12.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.week12.Data.DataSource.Remote.BooksAPI
import com.example.week12.Data.DataSource.Remote.RemoteDataSource
import com.example.week12.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week12.Data.Repository.BooksRepositoryImpl
import com.example.week12.Domain.UseCases.GetBooksBySearchUseCase
import com.example.week12.Presentation.ViewModels.NetworkViewModel
import com.example.week12.Presentation.theme.Week12Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var booksAPI: BooksAPI
    @Inject
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    @Inject
    lateinit var booksRepositoryImpl: BooksRepositoryImpl
    @Inject
    lateinit var getBooksBySearchUseCase: GetBooksBySearchUseCase
    private val networkViewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week12Theme {
                val books by networkViewModel.booksBySearch.collectAsState()
                val searchQuery by networkViewModel.searchQuery.collectAsState()

            }
        }
    }
}
