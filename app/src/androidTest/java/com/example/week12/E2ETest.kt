package com.example.week12

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.Models.BooksNetworkItem
import com.example.week12.Presentation.Models.ProfileData
import com.example.week12.Presentation.Screens.DetailSavedScreen
import com.example.week12.Presentation.Screens.DetailScreen
import com.example.week12.Presentation.Screens.MainScreen
import com.example.week12.Presentation.Screens.ProfileScreen
import com.example.week12.Presentation.Screens.SavedScreen
import com.example.week12.Presentation.Utils.BackgroundColorKey
import com.example.week12.Presentation.Utils.NavigationScreens
import com.example.week12.Presentation.theme.Week12Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)

class E2ETest {
    @get:Rule
    val composeRule = createComposeRule()
    val userList = MutableStateFlow<List<ProfileData?>>(emptyList())
    val currentUserFlow = MutableStateFlow<ProfileData?>(null)
    @Test
    fun `End-to-end test of main functionality with books`() = runTest {
        val databaseBooks = MutableStateFlow(
            listOf(
                BooksDatabaseItem(
                    id = 1,
                    title = "Test Book 1",
                    image = "Image 1",
                    description = "Description 1",
                    rating = 3.3,
                    publishedAt = "2100"
                )
            )
        )
        val networkBooks = MutableStateFlow(
            listOf(
                BooksNetworkItem(
                    title = "Test Book 1",
                    image = "Image 1",
                    description = "Description 1",
                    rating = 3.3,
                    publishedAt = "2100"
                )
            )
        )
        composeRule.setContent {
            Week12Theme {
                val navController = rememberNavController()
                var searchQuery by remember { mutableStateOf("Romantic") }
                val testNetworkBooks by networkBooks.collectAsState()
                val testDatabaseBooks by databaseBooks.collectAsState()
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.MainScreen
                ) {
                    composable<NavigationScreens.MainScreen> {
                        MainScreen(
                            books = testNetworkBooks,
                            navigateToDetailScreen = {
                                navController.navigate(
                                    NavigationScreens.DetailScreen(
                                        it
                                    )
                                )
                            },
                            onSearchQueryChanged = { searchQuery = it },
                            searchQuery = searchQuery,
                            onCancelNewSearchBooks = { searchQuery = " " },
                            navigateToProfilePage = {},
                            changeTheme = {},
                            theme = AppTheme.DARK,
                            changeLanguage = {},
                            navigateToSavedScreen = { navController.navigate(NavigationScreens.SavedScreen) },
                            navigateToMainScreen = { navController.navigate(NavigationScreens.MainScreen) }
                        )
                    }
                    composable<NavigationScreens.DetailScreen> { navBackStackEntry ->
                        val bookId: NavigationScreens.DetailScreen = navBackStackEntry.toRoute()
                        val certainBook = testNetworkBooks.find { it.title == bookId.id }
                        DetailScreen(
                            certainBook = certainBook,
                            navigateToMainScreen = {
                                navController.popBackStack()
                            },
                            theme = AppTheme.DARK,
                            savedBooksIds = testDatabaseBooks.map { it.title }.toSet(),
                            insertNewBook = { book ->
                                databaseBooks.value += book
                            }
                        )
                    }
                    composable<NavigationScreens.DetailSavedScreen> { navBackStackEntry ->
                        val bookId: NavigationScreens.DetailScreen = navBackStackEntry.toRoute()
                        val certainBook = testDatabaseBooks.find { it.title == bookId.id }
                        DetailSavedScreen(
                            certainBook = certainBook,
                            navigateToMainScreen = {
                                navController.popBackStack()
                            },
                            theme = AppTheme.DARK,
                        )
                    }
                    composable<NavigationScreens.SavedScreen> {
                        SavedScreen(
                            savedBooks = testDatabaseBooks,
                            navigateToDetailScreen = { itemId ->
                                navController.navigate(NavigationScreens.DetailSavedScreen(id = itemId))
                            },
                            navigateToProfilePage = {},
                            changeTheme = {},
                            theme = AppTheme.DARK,
                            changeLanguage = {},
                            back = { navController.popBackStack() },
                            navigateToMainScreen = {
                                navController.navigate(NavigationScreens.MainScreen)
                            },
                            navigateToSavedScreen = {
                                navController.navigate(NavigationScreens.SavedScreen)
                            },
                            deleteBook = { book ->
                                databaseBooks.value -= book
                            }
                        )
                    }
                }
            }
        }
        composeRule.onNodeWithTag("Books").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("SearchIcon").performClick()
        composeRule.onNodeWithTag("TextField").assertTextEquals("Romantic")
        composeRule.onNodeWithContentDescription("Cross").performClick()
        composeRule.onNodeWithTag("TextField").assertTextEquals(" ")
        composeRule.onNodeWithTag("Books").assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription("Arrow").performClick()

        composeRule.onNodeWithTag("Books").assertIsDisplayed()
        composeRule.onNodeWithTag("Card").assertIsDisplayed().performClick()
        composeRule.onNodeWithContentDescription("PictureDetailScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailScreenTitle").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailScreenDescription").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailScreenRating").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailScreenPublishedAt").assertIsDisplayed()
        composeRule.onNodeWithTag("SaveButton").assertIsDisplayed().performClick()
        composeRule.onNodeWithContentDescription("BackButtonDetailScreen").performClick()

        composeRule.onNodeWithTag("Books").assertIsDisplayed()
        composeRule.onNodeWithTag("CardSavedScreen").assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription("SavedIcon").performClick()

        composeRule.onNodeWithTag("SavedBooks").assertIsDisplayed()
        composeRule.onNodeWithTag("CardSavedScreen").assertIsDisplayed().performClick()
        composeRule.onNodeWithContentDescription("PictureDetailSavedScreen").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailSavedScreenTitle").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailSavedScreenDescription").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailSavedScreenRating").assertIsDisplayed()
        composeRule.onNodeWithTag("DetailSavedScreenPublishedAt").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("BackButtonDetailSavedScreen").performClick()

        composeRule.onNodeWithTag("SavedBooks").assertIsDisplayed()
        composeRule.onNodeWithTag("CardSavedScreen").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("DeleteIcon").performClick()
        composeRule.onNodeWithTag("CardSavedScreen").assertIsNotDisplayed()
        composeRule.onNodeWithContentDescription("BackIconSavedScreen").performClick()

        composeRule.onNodeWithTag("Books").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("SavedIcon").performClick()
        composeRule.onNodeWithTag("SavedBooks").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("MainIcon").performClick()
        composeRule.onNodeWithTag("Books").assertIsDisplayed()
    }

    @Test
    fun `End-to-end test of profile functionality`() {

        composeRule.setContent {
            val currentUser by currentUserFlow.collectAsState()
            Week12Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.MainScreen
                ) {
                    composable<NavigationScreens.MainScreen> {
                        MainScreen(
                            books = emptyList(),
                            navigateToDetailScreen = {},
                            onSearchQueryChanged = {},
                            searchQuery = "",
                            onCancelNewSearchBooks = {},
                            navigateToProfilePage = { navController.navigate(NavigationScreens.ProfileScreen)},
                            changeTheme = {},
                            theme = AppTheme.DARK,
                            changeLanguage = {},
                            navigateToSavedScreen = {},
                            navigateToMainScreen = {}
                        )
                    }
                    composable<NavigationScreens.ProfileScreen> {
                        ProfileScreen(
                            backIcon = { navController.popBackStack() },
                            currentUser = currentUser,
                            insertNewUser = {user->
                                userList.value += user
                                currentUserFlow.value = user
                            },
                            loginUser = {nickname,password->
                                loginUser(nickname,password)
                            },
                            logoutUser = {
                                currentUserFlow.value = null
                            }
                        )
                    }
                }
            }
        }
        composeRule.onNodeWithTag("Books").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("ProfileIcon").assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("TextUnauthenticated").assertIsDisplayed()

        composeRule.onNodeWithTag("Create").performClick()
        composeRule.onNodeWithTag("TextUnauthenticated").assertIsNotDisplayed()
        composeRule.onNodeWithTag("NicknameCreateTextField").performTextInput("Ryqit")
        composeRule.onNodeWithTag("FullNameCreateTextField").performTextInput("raf milk coffee")
        composeRule.onNodeWithTag("PasswordCreateTextField").performTextInput("5566")
        composeRule.onNodeWithTag("CreateProfileButton").performClick()

        composeRule.onNodeWithContentDescription("Avatar").assertIsDisplayed()
        composeRule.onNodeWithTag("Nickname").assertTextEquals("Ryqit")
        composeRule.onNodeWithTag("FullName").assertTextEquals("raf milk coffee")
        composeRule.onNodeWithTag("LogoutButton").performClick()

        composeRule.onNodeWithTag("TextUnauthenticated").assertIsDisplayed()
        composeRule.onNodeWithTag("Login").performClick()
        composeRule.onNodeWithTag("TextUnauthenticated").assertIsNotDisplayed()
        composeRule.onNodeWithTag("NicknameLoginTextField").performTextInput("Ryqit")
        composeRule.onNodeWithTag("PasswordLoginTextField").performTextInput("5566")
        composeRule.onNodeWithTag("LoginProfileButton").performClick()
        composeRule.onNodeWithTag("Nickname").assertTextEquals("Ryqit")
        composeRule.onNodeWithTag("FullName").assertTextEquals("raf milk coffee")
        composeRule.onNodeWithTag("LogoutButton").performClick()

        composeRule.onNodeWithContentDescription("BackFromProfile").performClick()
        composeRule.onNodeWithTag("Books").assertIsDisplayed()
    }

    @Test
    fun `End-to-end test of settings functionality`() {
        val flowAppTheme = MutableStateFlow(AppTheme.DARK)
        composeRule.setContent {
            val context = LocalContext.current
            var currentLanguage by remember { mutableStateOf("en") }
            val updatedContext = remember(currentLanguage) {
                setAppLocale(context, currentLanguage)
            }
            CompositionLocalProvider(LocalContext provides updatedContext) {
                val themeState by flowAppTheme.collectAsState(initial = AppTheme.DARK)
                Week12Theme(
                    darkTheme = when (themeState) {
                        AppTheme.DARK -> true
                        AppTheme.LIGHT -> false
                    }
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreens.MainScreen
                    ) {
                        composable<NavigationScreens.MainScreen> {
                            MainScreen(
                                books = emptyList(),
                                navigateToDetailScreen = {},
                                onSearchQueryChanged = {},
                                searchQuery = "",
                                onCancelNewSearchBooks = {},
                                navigateToProfilePage = {},
                                changeTheme = {
                                    flowAppTheme.value =
                                        if (flowAppTheme.value == AppTheme.DARK) AppTheme.LIGHT
                                        else AppTheme.DARK
                                },
                                theme = themeState,
                                changeLanguage = {
                                    currentLanguage = if (currentLanguage == "en") "ru" else "en"
                                },
                                navigateToSavedScreen = {},
                                navigateToMainScreen = {}
                            )
                        }
                    }
                }
            }
        }
        val colorLight = Color.LightGray
        val colorDark = Color.Black
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithText("Books").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Menu").assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Change app language")
            .assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Книги").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Menu").assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Поменять язык приложения")
            .assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Books").assertIsDisplayed()

        composeRule.onNodeWithTag("TopBar").assert(hasBackgroundColor(colorDark))
        composeRule.onNodeWithContentDescription("Menu").assertIsDisplayed().performClick()
        composeRule.onNodeWithText(context.getString(R.string.ChangeTheme))
            .assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("TopBar").assert(hasBackgroundColor(colorLight))
        composeRule.onNodeWithContentDescription("Menu").assertIsDisplayed().performClick()
        composeRule.onNodeWithText(context.getString(R.string.ChangeTheme))
            .assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("TopBar").assert(hasBackgroundColor(colorDark))
    }
    private fun setAppLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)


        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        config.setLocales(LocaleList(locale))
        val newContext = context.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
        return newContext
    }

    private fun hasBackgroundColor(expected: Color): SemanticsMatcher =
        SemanticsMatcher.expectValue(BackgroundColorKey, expected)

    private fun loginUser(nickname: String, password: String): Boolean {
        val user = userList.value.find {
            it?.nickName == nickname && it.password == password
        }
        return if (user != null) {
            currentUserFlow.value = user
            true
        } else {
            false
        }
    }
}