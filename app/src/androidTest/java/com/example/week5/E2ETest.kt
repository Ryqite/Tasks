package com.example.week5

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.week5.Data.Product
import com.example.week5.Data.Screen
import com.example.week5.UIcomponents.*
import com.example.week5.ui.theme.Week5Theme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class E2ETest {
    @get:Rule
    val rule = createComposeRule()
    private val sampleProducts = mutableListOf(
        Product(
            1,
            "Тестовый продукт",
            "https://www.samsungstore.ru/_next/image/?url=https%3A%2F%2Fcdn.samsungstore.ru%2Fiblock%2Ff89%2Ff89005113fc4a94e95ec29e6569aa810%2Fba0adfa23f3c27e93226fe55c4ce03d9.jpg&w=1080&q=75",
            "Описание"
        ),
        Product(
            2,
            "Товар 2",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVzIdiL-k95-9u4D2g17fkzer1NWzo-fVj6w&s",
            "Описание 2"
        )
    )
    val product = Product(1, "Test Product", "", "Description")

    @Test
    fun `end-to-end-test-of-purchasing-of-stuff-and-language-change`() {
        rule.setContent {

            val context = LocalContext.current
            var currentLanguage by remember { mutableStateOf(Locale.getDefault().language) }
            val currentNavigationState = remember { mutableStateOf<Screen>(Screen.ProductsScreen) }
            var currentProductId by remember { mutableStateOf(0) }
            Week5Theme {
                val productsForNav =
                    remember { mutableStateListOf<Product>().apply { addAll(sampleProducts) } }
                val basket = remember { mutableStateListOf<Product>() }
                val controller = rememberNavController()
                NavHost(navController = controller, startDestination = Screen.ProductsScreen) {
                    composable<Screen.ProductsScreen> {
                        ProductsScreen(
                            products = productsForNav,
                            profileScreen = {
                                currentNavigationState.value = Screen.ProfileScreen
                                controller.navigate(Screen.ProductsScreen)
                            },
                            detailScreen = { productId ->
                                currentNavigationState.value = Screen.DetailScreen(id = productId)
                                currentProductId = productId
                                controller.navigate(Screen.DetailScreen(id = productId))
                            },
                            navigateToProductPage = {
                                currentNavigationState.value = Screen.ProductsScreen
                                controller.navigate(Screen.ProductsScreen) {
                                    launchSingleTop = true
                                }
                            },
                            navigateToBasketPage = { controller.navigate(Screen.BasketScreen) },
                            changeLanguage = {
                                currentLanguage = if (currentLanguage == "en") "ru" else "en"
                                setAppLocale(context, currentLanguage)
                            }
                        )
                    }
                    composable<Screen.DetailScreen> { backStackEntry ->
                        val productId: Screen.DetailScreen = backStackEntry.toRoute()
                        val product = sampleProducts.find { it.id == productId.id }
                        DetailScreen(
                            product = product,
                            addToBascket = { product?.let { basket.add(it) } },
                            backIcon = { controller.popBackStack() },
                            navigateToProductPage = {
                                currentNavigationState.value = Screen.ProductsScreen
                                controller.navigate(Screen.ProductsScreen)
                            },
                            navigateToBasketPage = { controller.navigate(Screen.BasketScreen) }
                        )
                    }
                    composable<Screen.BasketScreen> {
                        BasketScreen(
                            productsForBuy = basket,
                            deleteFromBasket = { id -> basket.removeAll { it.id == id } },
                            backIcon = { controller.popBackStack() },
                            navigateToProductPage = {
                                when (currentNavigationState.value) {
                                    Screen.ProfileScreen -> {
                                        controller.navigate(Screen.ProductsScreen)
                                        controller.navigate(Screen.ProfileScreen)
                                    }

                                    Screen.ProductsScreen -> {
                                        currentNavigationState.value = Screen.ProductsScreen
                                        controller.navigate(Screen.ProductsScreen)
                                    }

                                    Screen.DetailScreen(id = currentProductId) -> {
                                        controller.navigate(Screen.ProductsScreen)
                                        controller.navigate(Screen.DetailScreen(currentProductId))
                                    }

                                    else -> {
                                        Log.e("NavigationError", "Wrong route")
                                        Unit
                                    }
                                }
                            },
                            navigateToBasketPage = { controller.navigate(Screen.BasketScreen) }
                        )
                    }
                    composable<Screen.ProfileScreen> {
                        ProfileScreen(
                            backIcon = {
                                currentNavigationState.value = Screen.ProductsScreen
                                controller.popBackStack()
                            },
                            navigateToBasketPage = { controller.navigate(Screen.BasketScreen) },
                            navigateToProductPage = {
                                currentNavigationState.value = Screen.ProductsScreen
                                controller.navigate(Screen.ProductsScreen)
                            }
                        )
                    }

                }
            }
        }
        rule.apply {
            onNodeWithText("Products").assertIsDisplayed()
            onNodeWithContentDescription("LanguageChange").performClick()
            onNodeWithText("Продукты").assertIsDisplayed()
            onNodeWithContentDescription("LanguageChange").performClick()
            onNodeWithTag("Products").assertIsDisplayed()
            onNodeWithText("Test Product").assertIsDisplayed().performClick()
            onNodeWithTag("Details").assertIsDisplayed()
            onNodeWithText("Add to basket").assertIsDisplayed().performClick()
            onNodeWithContentDescription("BasketButton").assertIsDisplayed().performClick()
            onNodeWithTag("Basket").assertIsDisplayed()
            onNodeWithText("Test Product").assertIsDisplayed()
            onAllNodesWithContentDescription("DeleteButton")[0].assertIsDisplayed().performClick()
            onNodeWithText("Basket is empty").assertIsDisplayed()
        }
    }
}
