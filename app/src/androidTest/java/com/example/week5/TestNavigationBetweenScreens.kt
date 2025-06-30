package com.example.week5

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.week5.ui.theme.Week5Theme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestNavigationBetweenScreens {
    @get:Rule
    val rule = createComposeRule()
    private val sampleProducts = mutableListOf(
        Product(1, "Тестовый продукт", "", "Описание"),
        Product(2, "Товар 2", "", "Описание 2")
    )
    val product = Product(1, "Тестовый продукт", "", "Описание")

    @Test
    fun testProfileScreen() {
        rule.setContent {
            Week5Theme {
                ProfileScreen(backIcon = {})
            }
        }
        rule.onNodeWithText("Ryqite").assertIsDisplayed()
        rule.onNodeWithText("Гарбарук Даниил Александрович").assertIsDisplayed()
        rule.onNodeWithContentDescription("Аватар").assertIsDisplayed()
        rule.onNodeWithText("Продукты").assertIsDisplayed()
    }

    @Test
    fun testProductsScreen(){
        rule.setContent {
            Week5Theme {
                ProductsScreen(
                    products = sampleProducts,
                    profileScreen = {},
                    detailScreen = {},
                    navigateToProductPage = {},
                    navigateToBascketPage = {}
                )
            }
        }
        rule.onNodeWithText("Тестовый продукт").assertIsDisplayed()
        rule.onNodeWithText("Продукты").assertIsDisplayed()
        rule.onNodeWithContentDescription("ПрофильКнопка").assertIsDisplayed()
        rule.onNodeWithContentDescription("ПродуктыКнопка").assertIsDisplayed()
        rule.onNodeWithContentDescription("КорзинаКнопка").assertIsDisplayed()
    }

    @Test
    fun testDetailScreen(){
        rule.setContent {
            Week5Theme {
                DetailScreen(
                    product = product,
                    addToBascket = {},
                    backIcon = {},
                    navigateToBascketPage = {},
                    navigateToProductPage = {})
            }
        }
        rule.onNodeWithText("Детали").assertIsDisplayed()
        rule.onNodeWithText("Тестовый продукт").assertIsDisplayed()
        rule.onNodeWithContentDescription("НазадИзДеталей").assertIsDisplayed()

    }
    @Test
    fun testBasketScreen(){
        val products = mutableStateListOf<Product>()
            .apply { addAll(sampleProducts) }
        var deletedId = 0
        rule.setContent {
            Week5Theme {
                BasketScreen(
                    productsForBuy = products,
                    deleteFromBasket = {deletedId = it},
                    backIcon = {},
                    navigateToProductPage = {},
                    navigateToBascketPage = {})
            }
        }
        rule.onNodeWithText("Корзина").assertIsDisplayed()
        rule.onNodeWithContentDescription("НазадИзКорзины").assertIsDisplayed()
        rule.onNodeWithContentDescription("УдалитьКнопка").performClick()
        assertEquals(1,deletedId)
    }
    @Test
    fun testNavigationBetweenScreens() {
        rule.setContent {
            Week5Theme {
                val productsForNav = remember { mutableStateListOf<Product>().apply { addAll(testProducts) } }
                val basket = remember { mutableStateListOf<Product>() }
                val controller = rememberNavController()
                NavHost(navController = controller, startDestination = "ProductsScreen") {
                    composable("ProductsScreen") {
                        ProductsScreen(
                            products = sampleProducts,
                            profileScreen = { controller.navigate("ProfileScreen") },
                            detailScreen = { controller.navigate("DetailScreen/$it") },
                            navigateToProductPage = { controller.navigate("ProductsScreen") },
                            navigateToBascketPage = { controller.navigate("BasketScreen") }
                        )
                    }
                    composable(
                        "DetailScreen/{productId}",
                        arguments = listOf(navArgument("productId") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        val product = productsForNav.firstOrNull { it.id == productId }
                        DetailScreen(
                            product = product,
                            addToBascket = { product?.let { basket.add(it) } },
                            backIcon = { controller.popBackStack() },
                            navigateToProductPage = { controller.navigate("ProductsScreen") },
                            navigateToBascketPage = { controller.navigate("BasketScreen") }
                        )
                    }
                    composable("BasketScreen") {
                        BasketScreen(
                            productsForBuy = basket,
                            deleteFromBasket = { id -> basket.removeAll { it.id == id } },
                            backIcon = { controller.popBackStack() },
                            navigateToProductPage = { controller.navigate("ProductsScreen") },
                            navigateToBascketPage = { controller.navigate("BasketScreen") }
                        )
                    }
                    composable("ProfileScreen") {
                        ProfileScreen(
                            backIcon = { controller.popBackStack() }
                        )
                    }

                }
            }
        }
        rule.onNodeWithContentDescription("ПрофильКнопка")
            .performClick()
        rule.onNodeWithText("Ryqite").assertIsDisplayed()
        rule.onNodeWithContentDescription("НазадИзПрофиля")
            .performClick()
        rule.onNodeWithText("Продукты").assertIsDisplayed()
        rule.onNodeWithContentDescription("КорзинаКнопка")
            .performClick()
        rule.onNodeWithText("Корзина").assertIsDisplayed()
        rule.onNodeWithContentDescription("НазадИзКорзины")
            .performClick()
        rule.onNodeWithText("Продукты").assertIsDisplayed()
        rule.onNodeWithText("Тестовый продукт")
            .performClick()
        rule.onNodeWithText("Описание").assertIsDisplayed()
        rule.onNodeWithContentDescription("НазадИзДеталей")
            .performClick()
        rule.onNodeWithText("Продукты").assertIsDisplayed()
        rule.onNodeWithContentDescription("КорзинаКнопка")
            .performClick()
        rule.onNodeWithContentDescription("ПродуктыКнопка")
            .performClick()
        rule.onNodeWithText("Продукты").assertIsDisplayed()
    }
    @Test
    fun testAddAndRemoveFromBasket() {
        val testingProduct = mutableStateListOf(product)
        val basket = mutableStateListOf<Product>()

        rule.setContent {
            Week5Theme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "ProductsScreen") {
                    composable("ProductsScreen") {
                        ProductsScreen(
                            products = testingProduct,
                            profileScreen = {},
                            detailScreen = { navController.navigate("DetailScreen/$it") },
                            navigateToProductPage = {},
                            navigateToBascketPage = { navController.navigate("BasketScreen") }
                        )
                    }
                    composable("DetailScreen/{productId}") { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        val product = testingProduct.first { it.id == productId }
                        DetailScreen(
                            product = product,
                            addToBascket = { basket.add(product) },
                            backIcon = { navController.popBackStack() },
                            navigateToProductPage = {},
                            navigateToBascketPage = {}
                        )
                    }
                    composable("BasketScreen") {
                        BasketScreen(
                            productsForBuy = basket,
                            deleteFromBasket = { id -> basket.removeAll { it.id == id } },
                            backIcon = { navController.popBackStack() },
                            navigateToProductPage = {},
                            navigateToBascketPage = {}
                        )
                    }
                }
            }
        }

        rule.onNodeWithText("Тест")
            .performClick()
        rule.onNodeWithText("Добавить в корзину")
            .performClick()
        rule.onNodeWithContentDescription("КорзинаКнопка")
            .performClick()
        rule.onNodeWithText("Тест")
            .assertIsDisplayed()
        rule.onNodeWithContentDescription("УдалитьКнопка")
            .performClick()
        rule.onNodeWithText("Корзина пуста")
            .assertIsDisplayed()
    }
}