package com.example.week9

import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.ui.test.*
import com.example.week9.Presentation.Utils.NavigationScreens
import com.example.week9.Presentation.Data.FilmsItem
import com.example.week9.Presentation.Data.ProfileData
import com.example.week9.Presentation.Screens.DetailScreen
import com.example.week9.Presentation.Screens.MainScreen
import com.example.week9.Presentation.Screens.ProfileScreen
import com.example.week9.Presentation.ViewModels.FilmsViewModel
import com.example.week9.Presentation.ViewModels.ProfileViewModel
import com.example.week9.Presentation.theme.week9Theme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class UITest {
    private val films = listOf(
        FilmsItem(
            "Котик",
            "https://static.tildacdn.com/tild3639-3964-4634-b239-393833386638/54080808080.png",
            "Офигенный фильм",
            32582352,
            "9.9"
        )
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationBetweenScreens() {
        composeTestRule.setContent {
            week9Theme {
                val navController = rememberNavController()
                var searchQuery by remember { mutableStateOf("f") }
                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.MainScreen
                ) {
                    composable<NavigationScreens.MainScreen> {
                        MainScreen(
                            films = films,
                            navigateToDetailScreen = { itemId ->
                                navController.navigate(NavigationScreens.DetailScreen(id = itemId))
                            },
                            onSearchQueryChanged = { searchQuery = it },
                            searchQuery = searchQuery,
                            onCancelNewSearchFilms = {searchQuery = " "},
                            navigateToProfilePage = { navController.navigate(NavigationScreens.ProfileScreen) }
                        )
                    }
                    composable<NavigationScreens.DetailScreen> { backStackEntry ->
                        val itemId: NavigationScreens.DetailScreen = backStackEntry.toRoute()
                        val certainFilms = films.find { it.kinopoiskId == itemId.id }
                        DetailScreen(
                            certainFilms = certainFilms,
                            navigateToMainScreen = { navController.popBackStack() }
                        )
                    }
                    composable<NavigationScreens.ProfileScreen> {
                        ProfileScreen(
                            data = ProfileData(
                                img = R.drawable.upside_down_gray_cat,
                                nickName = "Ryqite",
                                fullName = "Гарбарук Даниил Александрович"
                            ),
                            backIcon = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("Films").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Card").assertIsDisplayed().performClick()

        composeTestRule.onNodeWithTag("DetailAvatar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Description").assertIsDisplayed()
        composeTestRule.onNodeWithTag("RatingKinopoisk").assertIsDisplayed()
        composeTestRule.onNodeWithTag("KinopoiskId").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("BackButton").performClick()

        composeTestRule.onNodeWithTag("Films").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Card").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("ProfileIcon").performClick()
        composeTestRule.onNodeWithContentDescription("Avatar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Nickname").assertIsDisplayed()
        composeTestRule.onNodeWithTag("FullName").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("BackFromProfile").performClick()

        composeTestRule.onNodeWithTag("Films").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Card").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("SearchIcon").performClick()
        composeTestRule.onNodeWithTag("TextField").assertTextEquals("f")
        composeTestRule.onNodeWithContentDescription("Cross").performClick()
        composeTestRule.onNodeWithTag("TextField").assertTextEquals(" ")
        composeTestRule.onNodeWithTag("Films").assertIsNotDisplayed()
        composeTestRule.onNodeWithContentDescription("Arrow").performClick()

        composeTestRule.onNodeWithTag("Films").assertIsDisplayed()
    }
}