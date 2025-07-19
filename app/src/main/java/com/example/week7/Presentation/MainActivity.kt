package com.example.week7.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.week7.Presentation.theme.Week7Theme
import com.example.week7.Domain.NavigationScreens
import com.example.week7.NewsViewModel
import com.example.week7.Presentation.UIcomponents.DetailScreen
import com.example.week7.Presentation.UIcomponents.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModels<NewsViewModel>()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week7Theme {
                val news by viewModel.latestNews.collectAsState()
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
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}