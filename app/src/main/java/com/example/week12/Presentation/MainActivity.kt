package com.example.week12.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.week12.Data.DataSource.Local.BooksDao
import com.example.week12.Data.DataSource.Local.DataStoreSourceImpl
import com.example.week12.Data.DataSource.Local.LocalDataSourceImpl
import com.example.week12.Data.DataSource.Local.ProfileDataSourceImpl
import com.example.week12.Data.DataSource.Remote.BooksAPI
import com.example.week12.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week12.Data.Repository.BooksDatabaseRepositoryImpl
import com.example.week12.Data.Repository.BooksNetworkRepositoryImpl
import com.example.week12.Data.Repository.ProfileRepositoryImpl
import com.example.week12.Data.Repository.SettingsRepositoryImpl
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import com.example.week12.Domain.UseCases.DeleteBookUseCase
import com.example.week12.Domain.UseCases.GetAllBooksUseCase
import com.example.week12.Domain.UseCases.GetBooksBySearchUseCase
import com.example.week12.Domain.UseCases.GetProfileDataUseCase
import com.example.week12.Domain.UseCases.InsertNewBookUseCase
import com.example.week12.Domain.UseCases.UpdateBookUseCase
import com.example.week12.Presentation.Screens.DetailScreen
import com.example.week12.Presentation.Screens.MainScreen
import com.example.week12.Presentation.Screens.ProfileScreen
import com.example.week12.Presentation.Utils.NavigationScreens
import com.example.week12.Presentation.ViewModels.DatabaseViewModel
import com.example.week12.Presentation.ViewModels.NetworkViewModel
import com.example.week12.Presentation.ViewModels.ProfileViewModel
import com.example.week12.Presentation.ViewModels.SettingsViewModel
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
    lateinit var profileDataSource: ProfileDataSourceImpl
    @Inject
    lateinit var dataStoreSource: DataStoreSourceImpl

    @Inject
    lateinit var booksNetworkRepositoryImpl: BooksNetworkRepositoryImpl
    @Inject
    lateinit var booksDatabaseRepositoryImpl: BooksDatabaseRepositoryImpl
    @Inject
    lateinit var profileRepository: ProfileRepositoryImpl
    @Inject
    lateinit var settingsRepositoryImpl: SettingsRepositoryImpl

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
    @Inject
    lateinit var getProfileDataUseCase: GetProfileDataUseCase
    @Inject
    lateinit var changeDarkThemeUseCase: ChangeDarkThemeUseCase

    private val networkViewModel: NetworkViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeState by settingsViewModel.themeState.collectAsState(initial = AppTheme.DARK)
            Week12Theme(darkTheme = when(themeState){
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
            }) {
                val books by networkViewModel.booksBySearch.collectAsState()
                val searchQuery by networkViewModel.searchQuery.collectAsState()
                val savedBooks by databaseViewModel.booksFromDb.collectAsState()
                val profileData by profileViewModel.profileData.collectAsState()
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.MainScreen
                ){
                    composable<NavigationScreens.MainScreen> {
                        MainScreen(
                            books = books,
                            navigateToDetailScreen = {itemId->
                                navController.navigate(NavigationScreens.DetailScreen(id = itemId))
                            },
                            onSearchQueryChanged = {query->
                                networkViewModel.onSearchQueryChanged(query)},
                            searchQuery = searchQuery,
                            onCancelNewSearchBooks = {
                                networkViewModel.cancelCollector()
                            },
                            navigateToProfilePage = {
                                navController.navigate(NavigationScreens.ProfileScreen)
                            },
                            changeTheme = {
                                settingsViewModel.changeAppTheme()
                            }
                        )
                    }
                    composable<NavigationScreens.DetailScreen> {navBackStackEntry ->
                        val bookId: NavigationScreens.DetailScreen = navBackStackEntry.toRoute()
                        val certainBook = books.find { it.title == bookId.id }
                        DetailScreen(
                            certainBook = certainBook,
                            navigateToMainScreen = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable<NavigationScreens.ProfileScreen> {
                        ProfileScreen(data = profileData,backIcon = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
