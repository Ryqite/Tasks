package com.example.week5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.week5.Data.Product
import com.example.week5.Data.Screen
import com.example.week5.Data.testProducts
import com.example.week5.UIcomponents.*
import com.example.week5.ui.theme.Week5Theme

class MainActivity : ComponentActivity() {
    private lateinit var appSharedPreferences: AppSharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        val products = mutableStateListOf<Product>()
            .apply { addAll(testProducts) }
        val viewModel:BascetViewModel by viewModels()
        super.onCreate(savedInstanceState)
        appSharedPreferences = AppSharedPreferences(this)
        setAppLocale(this, appSharedPreferences.getLanguage())
        enableEdgeToEdge()
        setContent {
            val productsForBuy by viewModel.products.collectAsState()
            val context = LocalContext.current
            var currentLanguage by remember { mutableStateOf(appSharedPreferences.getLanguage()) }
            val updatedContext = remember(currentLanguage) {
                setAppLocale(context, currentLanguage).also {
                    appSharedPreferences.saveLanguage(currentLanguage)
                }
            }
            val currentNavigationState = remember { mutableStateOf<Screen>(Screen.ProductsScreen) }
            var currentProductId by remember { mutableStateOf(0) }
            Week5Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.ProductsScreen
                ) {
                    composable<Screen.ProductsScreen>(
                        deepLinks = listOf(
                            navDeepLink { uriPattern = "https://mainscreen" },
                            navDeepLink { uriPattern = "https://www.example.com" }
                        )
                    ) {
                        ProductsScreen(products,
                            profileScreen = {
                                currentNavigationState.value = Screen.ProfileScreen
                                navController.navigate(Screen.ProfileScreen)
                            },
                            detailScreen = { productId ->
                                currentNavigationState.value = Screen.DetailScreen(id = productId)
                                currentProductId = productId
                                navController.navigate(Screen.DetailScreen(id = productId))
                            },
                            navigateToProductPage = {
                                currentNavigationState.value = Screen.ProductsScreen
                                navController.navigate(Screen.ProductsScreen) {
                                    launchSingleTop = true
                                }
                            },
                            navigateToBasketPage = {
                                navController.navigate(Screen.BasketScreen)
                            },
                            changeLanguage = {
                                currentLanguage = if (currentLanguage == "en") "ru" else "en"
                            })
                    }
                    composable<Screen.DetailScreen>
                    { backStackEntry ->
                        val product: Screen.DetailScreen = backStackEntry.toRoute()
                        val certainProduct = products.find { it.id == product.id }
                        DetailScreen(certainProduct,
                            addToBascket = {
                                if (certainProduct != null) {
                                    viewModel.addProduct(certainProduct)
                                }
                            },
                            backIcon = {
                                currentNavigationState.value = Screen.ProductsScreen
                                navController.popBackStack()
                            },
                            navigateToProductPage = {
                                currentNavigationState.value = Screen.ProductsScreen
                                navController.navigate(Screen.ProductsScreen)
                            },
                            navigateToBasketPage = {
                                navController.navigate(Screen.BasketScreen)
                            })
                    }
                    composable<Screen.BasketScreen> {

                        BasketScreen(productsForBuy,
                            deleteFromBasket = { productId ->
                                viewModel.removeFirstProductById(productId)
                            },
                            backIcon = {
                                navController.popBackStack()
                            },
                            navigateToProductPage = {
                                when (currentNavigationState.value) {
                                    Screen.ProfileScreen -> {
                                        navController.navigate(Screen.ProductsScreen)
                                        navController.navigate(Screen.ProfileScreen)
                                    }

                                    Screen.ProductsScreen -> {
                                        currentNavigationState.value = Screen.ProductsScreen
                                        navController.navigate(Screen.ProductsScreen)
                                    }

                                    Screen.DetailScreen(id = currentProductId) -> {
                                        navController.navigate(Screen.ProductsScreen)
                                        navController.navigate(Screen.DetailScreen(currentProductId))
                                    }

                                    else -> {
                                        Log.e("NavigationError", "Wrong route")
                                        Unit
                                    }
                                }
                            },
                            navigateToBasketPage = {
                                navController.navigate(Screen.ProfileScreen) {
                                    launchSingleTop = true
                                }
                            })
                    }
                    composable<Screen.ProfileScreen> {
                        ProfileScreen(
                            backIcon = {
                                currentNavigationState.value = Screen.ProductsScreen
                                navController.popBackStack()
                            },
                            navigateToBasketPage = { navController.navigate(Screen.BasketScreen) },
                            navigateToProductPage = {
                                currentNavigationState.value = Screen.ProductsScreen
                                navController.navigate(Screen.ProductsScreen)
                            }
                        )
                    }

                }

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun TestPreview() {
}