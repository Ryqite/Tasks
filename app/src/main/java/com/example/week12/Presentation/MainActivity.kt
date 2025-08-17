package com.example.week12.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.week12.Data.DataSource.Local.BooksDao
import com.example.week12.Data.DataSource.Local.DataStoreSourceImpl
import com.example.week12.Data.DataSource.Local.LocalDataSourceImpl
import com.example.week12.Data.DataSource.Local.SharedPreferencesDataSourceImpl
import com.example.week12.Data.DataSource.Remote.BooksAPI
import com.example.week12.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week12.Data.Repository.BooksDatabaseRepositoryImpl
import com.example.week12.Data.Repository.BooksNetworkRepositoryImpl
import com.example.week12.Data.Repository.SettingsRepositoryImpl
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Domain.UseCases.ChangeDarkThemeUseCase
import com.example.week12.Domain.UseCases.DeleteBookUseCase
import com.example.week12.Domain.UseCases.GetAllBooksUseCase
import com.example.week12.Domain.UseCases.GetAllUsersUseCase
import com.example.week12.Domain.UseCases.GetBooksBySearchUseCase
import com.example.week12.Domain.UseCases.GetLanguageUseCase
import com.example.week12.Domain.UseCases.InsertNewBookUseCase
import com.example.week12.Domain.UseCases.InsertNewUserUseCase
import com.example.week12.Domain.UseCases.SaveLanguageUseCase
import com.example.week12.Domain.UseCases.SetAppLocaleUseCase
import com.example.week12.Domain.UseCases.UpdateBookUseCase
import com.example.week12.Presentation.Screens.DetailSavedScreen
import com.example.week12.Presentation.Screens.DetailScreen
import com.example.week12.Presentation.Screens.MainScreen
import com.example.week12.Presentation.Screens.ProfileScreen
import com.example.week12.Presentation.Screens.SavedScreen
import com.example.week12.Presentation.Utils.NavigationScreens
import com.example.week12.Presentation.ViewModels.DatabaseViewModel
import com.example.week12.Presentation.ViewModels.NetworkViewModel
import com.example.week12.Presentation.ViewModels.SettingsViewModel
import com.example.week12.Presentation.theme.Week12Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    @Inject
    lateinit var localDataSourceImpl: LocalDataSourceImpl
    @Inject
    lateinit var dataStoreSource: DataStoreSourceImpl
    @Inject
    lateinit var sharedPreferencesDataSourceImpl: SharedPreferencesDataSourceImpl

    @Inject
    lateinit var booksNetworkRepositoryImpl: BooksNetworkRepositoryImpl
    @Inject
    lateinit var booksDatabaseRepositoryImpl: BooksDatabaseRepositoryImpl
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
    lateinit var insertNewUserUseCase: InsertNewUserUseCase
    @Inject
    lateinit var getAllUsersUseCase: GetAllUsersUseCase

    @Inject
    lateinit var changeDarkThemeUseCase: ChangeDarkThemeUseCase
    @Inject
    lateinit var getLanguageUseCase: GetLanguageUseCase
    @Inject
    lateinit var saveLanguageUseCase: SaveLanguageUseCase
    @Inject
    lateinit var setAppLocaleUseCase: SetAppLocaleUseCase

    private val networkViewModel: NetworkViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel.setAppLocale(this,settingsViewModel.getLanguage())
        setContent {
            val context = LocalContext.current
            var currentLanguage by remember { mutableStateOf(settingsViewModel.getLanguage()) }
            val updatedContext = remember(currentLanguage) {
                settingsViewModel.setAppLocale(context, currentLanguage).also {
                    settingsViewModel.saveLanguage(currentLanguage)
                }
            }
            CompositionLocalProvider(LocalContext provides updatedContext) {
                val themeState by settingsViewModel.themeState.collectAsState(initial = AppTheme.DARK)
                Week12Theme(
                    darkTheme = when (themeState) {
                        AppTheme.DARK -> true
                        AppTheme.LIGHT -> false
                    }
                ) {
                    val books by networkViewModel.booksBySearch.collectAsState()
                    val searchQuery by networkViewModel.searchQuery.collectAsState()
                    val savedBooks by databaseViewModel.booksFromDb.collectAsState()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreens.MainScreen
                    ) {
                        composable<NavigationScreens.MainScreen> {
                            MainScreen(
                                books = books,
                                navigateToDetailScreen = { itemId ->
                                    navController.navigate(NavigationScreens.DetailScreen(id = itemId))
                                },
                                onSearchQueryChanged = { query ->
                                    networkViewModel.onSearchQueryChanged(query)
                                },
                                searchQuery = searchQuery,
                                onCancelNewSearchBooks = {
                                    networkViewModel.cancelCollector()
                                },
                                navigateToProfilePage = {
                                    navController.navigate(NavigationScreens.ProfileScreen)
                                },
                                changeTheme = {
                                    settingsViewModel.changeAppTheme()
                                },
                                theme = themeState,
                                changeLanguage = {
                                    currentLanguage = if(currentLanguage == "en") "ru" else "en"
                                },
                                navigateToSavedScreen = {
                                    navController.navigate(NavigationScreens.SavedScreen)
                                },
                                navigateToMainScreen = {
                                    navController.navigate(NavigationScreens.MainScreen)
                                }
                            )
                        }
                        composable<NavigationScreens.DetailScreen> { navBackStackEntry ->
                            val bookId: NavigationScreens.DetailScreen = navBackStackEntry.toRoute()
                            val certainBook = books.find { it.title == bookId.id }
                            DetailScreen(
                                certainBook = certainBook,
                                navigateToMainScreen = {
                                    navController.popBackStack()
                                },
                                theme = themeState,
                                viewModel = databaseViewModel
                            )
                        }
                        composable<NavigationScreens.DetailSavedScreen> { navBackStackEntry ->
                            val bookId: NavigationScreens.DetailScreen = navBackStackEntry.toRoute()
                            val certainBook = savedBooks.find { it.title == bookId.id }
                            DetailSavedScreen(
                                certainBook = certainBook,
                                navigateToMainScreen = {
                                    navController.popBackStack()
                                },
                                theme = themeState,
                            )
                        }
                        composable<NavigationScreens.ProfileScreen> {
                            ProfileScreen(
                                backIcon = { navController.popBackStack() },
                                viewModel = databaseViewModel
                            )
                        }
                        composable<NavigationScreens.SavedScreen> {
                            SavedScreen(
                                savedBooks = savedBooks,
                                navigateToDetailScreen = { itemId ->
                                    navController.navigate(NavigationScreens.DetailSavedScreen(id = itemId))
                                },
                                navigateToProfilePage = {
                                    navController.navigate(NavigationScreens.ProfileScreen)
                                },
                                changeTheme = {
                                    settingsViewModel.changeAppTheme()
                                },
                                theme = themeState,
                                changeLanguage = {
                                    currentLanguage = if(currentLanguage == "en") "ru" else "en"
                                },
                                back = { navController.popBackStack() },
                                navigateToMainScreen = {
                                    navController.navigate(NavigationScreens.MainScreen)
                                },
                                navigateToSavedScreen = {
                                    navController.navigate(NavigationScreens.SavedScreen)
                                },
                                viewModel = databaseViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
