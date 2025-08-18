package com.example.week12

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.Models.BooksNetworkItem
import com.example.week12.Presentation.Screens.DetailSavedScreen
import com.example.week12.Presentation.Screens.DetailScreen
import com.example.week12.Presentation.Screens.MainScreen
import com.example.week12.Presentation.Screens.ProfileScreen
import com.example.week12.Presentation.Screens.SavedScreen
import com.example.week12.Presentation.Utils.NavigationScreens
import com.example.week12.Presentation.ViewModels.DatabaseViewModel
import com.example.week12.Presentation.ViewModels.NetworkViewModel
import com.example.week12.Presentation.theme.Week12Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class E2ETest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `End-to-end test of main functionality with books`() = runTest {
        val databaseBooks = MutableStateFlow(
            listOf(
            BooksDatabaseItem(id = 1, title = "Test Book 1", image = "Image 1", description = "Description 1", rating = 3.3, publishedAt = "2100")))
        val networkBooks = MutableStateFlow(
            listOf(
            BooksNetworkItem(title = "Test Book 1", image = "Image 1", description = "Description 1", rating = 3.3, publishedAt = "2100")))
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
                            navigateToDetailScreen = { navController.navigate(NavigationScreens.DetailScreen(it)) },
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
                            insertNewBook = {book->
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
                            deleteBook = {book->
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

    }

    @Test
    fun `End-to-end test of settings functionality`() {

    }
}