package com.example.week5

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.week5.Data.Product
import com.example.week5.ui.theme.Week5Theme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITests {
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
        rule.onNodeWithContentDescription("НазадИзПрофиля").assertIsDisplayed()
    }

    @Test
    fun testProductsScreen() {
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
    fun testDetailScreen() {
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
    fun testBasketScreen() {
        val products = mutableStateListOf<Product>()
            .apply { addAll(sampleProducts) }
        var deletedId = 0
        rule.setContent {
            Week5Theme {
                BasketScreen(
                    productsForBuy = products,
                    deleteFromBasket = { deletedId = it },
                    backIcon = {},
                    navigateToProductPage = {},
                    navigateToBascketPage = {})
            }
        }
        rule.onNodeWithText("Корзина").assertIsDisplayed()
        rule.onNodeWithContentDescription("НазадИзКорзины").assertIsDisplayed()
        rule.onAllNodesWithContentDescription("УдалитьКнопка")[0].performClick()
        assertEquals(1, deletedId)
    }

    @Test
    fun testNavigationBetweenScreens() {
        rule.setContent {
            Week5Theme {
                val productsForNav =
                    remember { mutableStateListOf<Product>().apply { addAll(sampleProducts) } }
                val basket = remember { mutableStateListOf<Product>() }
                val controller = rememberNavController()
                NavHost(navController = controller, startDestination = "ProductsScreen") {
                    composable("ProductsScreen") {
                        ProductsScreen(
                            products = productsForNav,
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
                        val product = productsForNav.find { it.id == productId }
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
        rule.apply {
            onNodeWithContentDescription("ПрофильКнопка").performClick()
            onNodeWithText("Ryqite").assertIsDisplayed()
            onNodeWithContentDescription("НазадИзПрофиля").performClick()
            onNodeWithText("Продукты").assertIsDisplayed()
            onNodeWithContentDescription("КорзинаКнопка").performClick()
            onNodeWithText("Корзина").assertIsDisplayed()
            onNodeWithContentDescription("НазадИзКорзины").performClick()
            onNodeWithText("Продукты").assertIsDisplayed()
            onNodeWithText("Тестовый продукт").performClick()
            onNodeWithText("Детали").assertIsDisplayed()
            onNodeWithText("Добавить в корзину").assertIsDisplayed()
            onNodeWithText("Добавить в корзину").performClick()
            onNodeWithContentDescription("НазадИзДеталей").performClick()
            onNodeWithText("Продукты").assertIsDisplayed()
            onNodeWithContentDescription("КорзинаКнопка").performClick()
            onNodeWithText("Тестовый продукт").assertIsDisplayed()
            onAllNodesWithContentDescription("УдалитьКнопка")[0].performClick()
            onNodeWithText("Корзина пуста").assertIsDisplayed()
            onNodeWithContentDescription("ПродуктыКнопка").performClick()
            onNodeWithText("Продукты").assertIsDisplayed()
        }
    }
}