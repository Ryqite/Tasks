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
import com.example.week5.Data.Product
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
                    startDestination = "ProductsScreen"
                ) {
                    composable("ProductsScreen") {
                        ProductsScreen(products,
                            profileScreen = {
                                navController.navigate("ProfileScreen")
                            },
                            detailScreen = { productId ->
                                navController.navigate("DetailScreen/$productId")
                            },
                            navigateToProductPage = {
                                navController.navigate("ProductsScreen")
                            },
                            navigateToBasketPage = {
                                navController.navigate("BasketScreen")
                            })
                    }
                    composable(
                        route = "DetailScreen/{productId}",
                        arguments = listOf(
                            navArgument("productId") {
                                type = NavType.IntType
                                defaultValue = 0
                                nullable = false
                            }),
                        deepLinks = listOf(
                            navDeepLink { uriPattern = "https://example.com/DetailScreen/{productId}" }
                        )
                    )
                    { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        val certainProduct = products.find { it.id == productId }
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
                                navController.navigate("ProductsScreen")
                            },
                            navigateToBasketPage = {
                                navController.navigate("BasketScreen")
                            })
                    }
                    composable("BasketScreen") {
                        BasketScreen(productsForBuy,
                            deleteFromBasket = { productId ->
                                productsForBuy.removeAll { it.id == productId }
                            },
                            backIcon = {
                                navController.popBackStack()
                            },
                            navigateToProductPage = {
                                navController.navigate("ProductsScreen")
                            },
                            navigateToBasketPage = {
                                navController.navigate("BasketScreen")
                            })
                    }
                    composable("ProfileScreen") {
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