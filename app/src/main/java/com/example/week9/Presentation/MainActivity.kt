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
import com.example.week9.Data.DataSource.Remote.FilmsAPI
import com.example.week9.Data.DataSource.Remote.RemoteDataSourceImpl
import com.example.week9.Data.Repository.FilmsRepositoryImpl
import com.example.week9.Domain.GetLatestFilmsUseCase
import com.example.week9.Presentation.theme.week9Theme
import com.example.week9.Presentation.Utils.NavigationScreens
import com.example.week9.Presentation.Screens.DetailScreen
import com.example.week9.Presentation.Screens.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var retrofitInstance: FilmsAPI
    @Inject
    lateinit var remoteDataSource: RemoteDataSourceImpl
    @Inject
    lateinit var repository: FilmsRepositoryImpl
    @Inject
    lateinit var getLatestFilmsUseCase: GetLatestFilmsUseCase
    private val viewModel: FilmsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            week9Theme {
                val films by viewModel.latestfilms.collectAsState()
                val searchQuery by viewModel.searchQuery.collectAsState()
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
                            onSearchQueryChanged = {query->
                                viewModel.onSearchQueryChanged(query)},
                            searchQuery = searchQuery,
                            onCancelNewSearchFilms = {
                                viewModel.cancelSearch()
                            }
                        )
                    }
                    composable<NavigationScreens.DetailScreen> { backStackEntry->
                        val itemId: NavigationScreens.DetailScreen = backStackEntry.toRoute()
                        val certainFilms = films.find { it.kinopoiskId == itemId.id }
                        DetailScreen(
                            certainFilms = certainFilms,
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