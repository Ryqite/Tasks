package com.example.week5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.week5.Data.Product
import com.example.week5.Data.Screen
import com.example.week5.Data.testProducts
import com.example.week5.UIcomponents.*
import com.example.week5.ui.theme.Week5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val products = mutableStateListOf<Product>()
            .apply { addAll(testProducts) }
        val productsForBuy = mutableStateListOf<Product>()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                                navController.navigate(Screen.ProfileScreen)
                            },
                            detailScreen = { productId ->
                                navController.navigate(Screen.DetailScreen(id = productId))
                            },
                            navigateToProductPage = {
                                navController.navigate(Screen.ProductsScreen)
                            },
                            navigateToBasketPage = {
                                navController.navigate(Screen.BasketScreen)
                            })
                    }
                    composable<Screen.DetailScreen>
                    { backStackEntry ->
                        val product: Screen.DetailScreen = backStackEntry.toRoute()
                        val certainProduct = products.find { it.id == product.id }
                        DetailScreen(certainProduct,
                            addToBascket = {
                                if (certainProduct != null) {
                                    productsForBuy.add(certainProduct)
                                }
                            },
                            backIcon = {
                                navController.popBackStack()
                            },
                            navigateToProductPage = {
                                navController.navigate(Screen.ProductsScreen)
                            },
                            navigateToBasketPage = {
                                navController.navigate(Screen.BasketScreen)
                            })
                    }
                    composable<Screen.BasketScreen> {
                        BasketScreen(productsForBuy,
                            deleteFromBasket = { productId ->
                                productsForBuy.removeAll { it.id == productId }
                            },
                            backIcon = {
                                navController.popBackStack()
                            },
                            navigateToProductPage = {
                                navController.navigate(Screen.ProductsScreen)
                            },
                            navigateToBasketPage = {
                                navController.navigate(Screen.ProfileScreen)
                            })
                    }
                    composable<Screen.ProfileScreen> {
                        ProfileScreen(
                            backIcon = {
                                navController.popBackStack()
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