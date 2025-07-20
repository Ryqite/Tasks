package com.example.week7

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.week7.Domain.News
import androidx.compose.ui.test.*
import com.example.week7.Domain.NavigationScreens
import com.example.week7.Presentation.NewsItem
import com.example.week7.Presentation.UIcomponents.DetailScreen
import com.example.week7.Presentation.UIcomponents.MainScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITest {
    val news = listOf(
        NewsItem("Котик",
        "https://static.tildacdn.com/tild3639-3964-4634-b239-393833386638/54080808080.png",
        "https://terraria-calamity-mod.fandom.com/ru/wiki/Бездна#Советы",
        "Котик любит играть")
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
                        news = news,
                        navigateToDetailScreen = { itemId ->
                            navController
                                .navigate(NavigationScreens.DetailScreen(title = itemId))
                        }
                    )
                }
                composable<NavigationScreens.DetailScreen> { backStackEntry->
                    val itemId: NavigationScreens.DetailScreen = backStackEntry.toRoute()
                    val certainNews = news.find { it.title == itemId.title }
                    DetailScreen(
                        certainNews = certainNews,
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