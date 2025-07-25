package com.example.week9.Presentation

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
import com.example.week9.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week9.Data.Repository.NewsRepositoryImpl
import com.example.week9.Data.DataSource.Remote.RetrofitInstance
import com.example.week9.Domain.GetLatestNewsUseCase
import com.example.week9.Presentation.theme.week9Theme
import com.example.week9.Presentation.Utils.NavigationScreens
import com.example.week9.Presentation.UIcomponents.DetailScreen
import com.example.week9.Presentation.UIcomponents.MainScreen

class MainActivity : ComponentActivity() {
    private val retrofitInstance by lazy { RetrofitInstance.api }
    private val remoteDataSource by lazy { RemoteDataSourceImpl(retrofitInstance) }
    private val repository by lazy { NewsRepositoryImpl(remoteDataSource) }
    private val getLatestNewsUseCase by lazy { GetLatestNewsUseCase(repository) }
    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(getLatestNewsUseCase)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            week9Theme {
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