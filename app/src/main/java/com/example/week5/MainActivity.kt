package com.example.week5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
                            navigateToBascketPage = {
                                navController.navigate("BasketScreen")
                            })
                    }
                    composable(
                        route = "DetailScreen/{productId}",
                        arguments = listOf(
                            navArgument("productId") {
                                type = NavType.IntType
                            })
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
                            navigateToBascketPage = {
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
                            navigateToBascketPage = {
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