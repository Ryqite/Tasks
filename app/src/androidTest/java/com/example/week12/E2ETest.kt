package com.example.week12

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.Screens.DetailSavedScreen
import com.example.week12.Presentation.Screens.DetailScreen
import com.example.week12.Presentation.Screens.MainScreen
import com.example.week12.Presentation.Screens.ProfileScreen
import com.example.week12.Presentation.Screens.SavedScreen
import com.example.week12.Presentation.Utils.NavigationScreens
import com.example.week12.Presentation.ViewModels.DatabaseViewModel
import com.example.week12.Presentation.ViewModels.NetworkViewModel
import com.example.week12.Presentation.theme.Week12Theme
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
//@HiltAndroidTest
//class E2ETest {
//    @get:Rule
//    val composeRule = createComposeRule()
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @BindValue
//    @JvmField
//    val fakeBookRepository: BookRepository = FakeBookRepository()
//
//    @BindValue
//    @JvmField
//    val fakeUserRepository: UserRepository = FakeUserRepository()
//
//    @Before
//    fun setup() {
//        hiltRule.inject()
//    }
//    @Test
//    fun `End-to-end test of main functionality with books`() = runTest {
//        val testBooks = listOf(
//            BooksDatabaseItem(id = 1, title = "Test Book 1", image = "Image 1", description = "Description 1", rating = 3.3, publishedAt = "2100"),
//            BooksDatabaseItem(id = 2, title = "Test Book 2", image = "Image 2", description = "Description 2", rating = 2.1, publishedAt = "1800")
//        )
//        // 1. Добавляем тестовые данные
//        fakeBookRepository.insertBook(
//            Book(
//                id = "1",
//                title = "Clean Code",
//                author = "Robert Martin",
//                description = "Software engineering best practices"
//            )
//        )
//
//        // 2. Запускаем приложение
//        composeRule.setContent {
//            Week12Theme {
//                val navController = rememberNavController()
//                val networkViewModel = hiltViewModel<NetworkViewModel>()
//                val databaseViewModel = hiltViewModel<DatabaseViewModel>()
//
//                NavHost(
//                    navController = navController,
//                    startDestination = NavigationScreens.MainScreen
//                ) {
//                    composable<NavigationScreens.MainScreen> {
//                        MainScreen(
//                            books = networkViewModel.booksBySearch.collectAsState().value,
//                            navigateToDetailScreen = { navController.navigate(NavigationScreens.DetailScreen(it)) },
//                            onSearchQueryChanged = { networkViewModel.onSearchQueryChanged(it) },
//                            searchQuery = networkViewModel.searchQuery.collectAsState().value,
//                            onCancelNewSearchBooks = { networkViewModel.cancelCollector() },
//                            navigateToProfilePage = { navController.navigate(NavigationScreens.ProfileScreen) },
//                            changeTheme = {},
//                            theme = AppTheme.DARK,
//                            changeLanguage = {},
//                            navigateToSavedScreen = { navController.navigate(NavigationScreens.SavedScreen) },
//                            navigateToMainScreen = { navController.navigate(NavigationScreens.MainScreen) },
//                            viewModel = databaseViewModel
//                        )
//                    }
//                    composable<NavigationScreens.DetailScreen> { navBackStackEntry ->
//                        val bookId: NavigationScreens.DetailScreen = navBackStackEntry.toRoute()
//                        val certainBook = books.find { it.title == bookId.id }
//                        DetailScreen(
//                            certainBook = certainBook,
//                            navigateToMainScreen = {
//                                navController.popBackStack()
//                            },
//                            theme = AppTheme.DARK,
//                            viewModel = databaseViewModel
//                        )
//                    }
//                    composable<NavigationScreens.DetailSavedScreen> { navBackStackEntry ->
//                        val bookId: NavigationScreens.DetailScreen = navBackStackEntry.toRoute()
//                        val certainBook = savedBooks.find { it.title == bookId.id }
//                        DetailSavedScreen(
//                            certainBook = certainBook,
//                            navigateToMainScreen = {
//                                navController.popBackStack()
//                            },
//                            theme = AppTheme.DARK,
//                        )
//                    }
//                    composable<NavigationScreens.ProfileScreen> {
//                        ProfileScreen(
//                            backIcon = {},
//                            viewModel = databaseViewModel
//                        )
//                    }
//                    composable<NavigationScreens.SavedScreen> {
//                        SavedScreen(
//                            savedBooks = savedBooks,
//                            navigateToDetailScreen = { itemId ->
//                                navController.navigate(NavigationScreens.DetailSavedScreen(id = itemId))
//                            },
//                            navigateToProfilePage = {},
//                            changeTheme = {},
//                            theme = AppTheme.DARK,
//                            changeLanguage = {},
//                            back = { navController.popBackStack() },
//                            navigateToMainScreen = {
//                                navController.navigate(NavigationScreens.MainScreen)
//                            },
//                            navigateToSavedScreen = {
//                                navController.navigate(NavigationScreens.SavedScreen)
//                            },
//                            viewModel = databaseViewModel
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    @Test
//    fun `End-to-end test of profile functionality`() {
//
//    }
//
//    @Test
//    fun `End-to-end test of settings functionality`() {
//
//    }
//}
//class FakeBookRepository : BookRepository {
//    private val books = mutableListOf<Book>()
//
//    override suspend fun insertBook(book: Book) {
//        books.add(book)
//    }
//
//    override suspend fun getBooks(): List<Book> = books
//}
//
//class FakeUserRepository : UserRepository {
//    private var currentUser: User? = null
//
//    override suspend fun login(username: String, password: String): Boolean {
//        currentUser = User(username)
//        return true
//    }
//
//    override fun getCurrentUser(): User? = currentUser
//}