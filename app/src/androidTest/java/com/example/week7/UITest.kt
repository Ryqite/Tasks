package com.example.week9

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.ui.test.*
import com.example.week9.Presentation.Utils.NavigationScreens
import com.example.week9.Presentation.Data.FilmsItem
import com.example.week9.Presentation.Screens.DetailScreen
import com.example.week9.Presentation.Screens.MainScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITest {
    val films = listOf(
        FilmsItem("Котик",
        "https://static.tildacdn.com/tild3639-3964-4634-b239-393833386638/54080808080.png",
            "Офигенный фильм",
        32582352,
        "9.9")
    )
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun ComplexUITest(){
        rule.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavigationScreens.MainScreen
            ) {
                composable<NavigationScreens.MainScreen> {
                    MainScreen(
                        films = films,
                        navigateToDetailScreen = { itemId ->
                            navController
                                .navigate(NavigationScreens.DetailScreen(id = itemId))
                        },
                        onCancelNewSearchFilms = {},
                        onSearchQueryChanged = {},
                        searchQuery = "",
                        navigateToProfilePage = {}
                    )
                }
                composable<NavigationScreens.DetailScreen> { backStackEntry->
                    val itemId: NavigationScreens.DetailScreen = backStackEntry.toRoute()
                    val certainfilms = films.find { it.kinopoiskId == itemId.id }
                    DetailScreen(
                        certainFilms = certainfilms,
                        navigateToMainScreen = { navController.popBackStack() }
                    )
                }
            }
        }
        rule.onNodeWithTag("Card").assertIsDisplayed().performClick()
        rule.onNodeWithTag("Card").assertIsNotDisplayed()
        rule.onNodeWithTag("DetailAvatar").assertIsDisplayed()
        rule.onNodeWithTag("Title").assertIsDisplayed()
        rule.onNodeWithTag("Description").assertIsDisplayed()
        rule.onNodeWithTag("source").assertIsDisplayed()
        rule.onNodeWithContentDescription("BackButton").assertIsDisplayed()
            .performClick()
        rule.onNodeWithTag("Card").assertIsDisplayed()
    }
}