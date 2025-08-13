package com.example.week12.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.week12.Data.DataSource.Local.BooksDao
import com.example.week12.Data.DataSource.Local.LocalDataSourceImpl
import com.example.week12.Data.DataSource.Remote.BooksAPI
import com.example.week12.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week12.Data.Repository.BooksDatabaseRepositoryImpl
import com.example.week12.Data.Repository.BooksNetworkRepositoryImpl
import com.example.week12.Domain.UseCases.DeleteBookUseCase
import com.example.week12.Domain.UseCases.GetAllBooksUseCase
import com.example.week12.Domain.UseCases.GetBooksBySearchUseCase
import com.example.week12.Domain.UseCases.InsertNewBookUseCase
import com.example.week12.Domain.UseCases.UpdateBookUseCase
import com.example.week12.Presentation.ViewModels.DatabaseViewModel
import com.example.week12.Presentation.ViewModels.NetworkViewModel
import com.example.week12.Presentation.theme.Week12Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var booksAPI: BooksAPI
    @Inject
    lateinit var booksDao: BooksDao

    @Inject
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    @Inject
    lateinit var localDataSourceImpl: LocalDataSourceImpl

    @Inject
    lateinit var booksNetworkRepositoryImpl: BooksNetworkRepositoryImpl
    @Inject
    lateinit var booksDatabaseRepositoryImpl: BooksDatabaseRepositoryImpl

    @Inject
    lateinit var getBooksBySearchUseCase: GetBooksBySearchUseCase
    @Inject
    lateinit var insertNewBookUseCase: InsertNewBookUseCase
    @Inject
    lateinit var updateBookUseCase: UpdateBookUseCase
    @Inject
    lateinit var deleteBookUseCase: DeleteBookUseCase
    @Inject
    lateinit var getAllBooksUseCase: GetAllBooksUseCase

    private val networkViewModel: NetworkViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week12Theme {
                val books by networkViewModel.booksBySearch.collectAsState()
                val searchQuery by networkViewModel.searchQuery.collectAsState()
                val savedBooks by databaseViewModel.booksFromDb.collectAsState()

            }
        }
    }
}
