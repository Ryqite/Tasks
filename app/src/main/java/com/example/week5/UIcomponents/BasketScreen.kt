package com.example.week5.UIcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.week5.Data.Product

/**
 * Экран корзины отображающий список товаров на покупку
 *
 * @param productsForBuy список продуктов который будет отображаться на данном экране
 * @param deleteFromBasket лямбда-функция для удаления конкретного продукта (принимает id
 * продукта, который удален из списка отображения)
 * @param backIcon лямбда-функция для возврата на экран [ProductsScreen]
 * @param navigateToProductPage лямбда-функция,передающаяся в [BottomBar] для перехода
 * на [ProductsScreen]
 * @param navigateToBasketPage лямбда-функция,передающаяся в [BottomBar] для перехода
 * на [BasketScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    productsForBuy: List<Product>,
    deleteFromBasket: (Int) -> Unit,
    backIcon: () -> Unit,
    navigateToProductPage: () -> Unit,
    navigateToBasketPage: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Корзина") },
                navigationIcon = {
                    IconButton(onClick = backIcon) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "НазадИзКорзины")
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                navigateToProductPage = navigateToProductPage,
                navigateToBascketPage = navigateToBasketPage
            )
        }
    ) { innerPadding ->
        if (productsForBuy.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Корзина пуста")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(productsForBuy) { product ->
                    BasketItem(
                        product = product,
                        onDelete = { deleteFromBasket(product.id) }
                    )
                }
            }
        }
    }
}
