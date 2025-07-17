package com.example.week5.UIcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.week5.Data.Product
import com.example.week5.R

/**
 * Главный экран, отображающий список продуктов
 *
 * @param products список продуктов который будет отображаться на данном экране
 * @param detailScreen лямбда-функция для перехода на экран [DetailScreen] (принимает id продукта,
 * который будет отображаться
 * @param profileScreen лямбда-функция для перехода на экран [ProfileScreen]
 * @param navigateToProductPage лямбда-функция,передающаяся в [BottomBar] для перехода
 * на [ProductsScreen]
 * @param navigateToBasketPage лямбда-функция,передающаяся в [BottomBar] для перехода
 * на [BasketScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    products: List<Product>,
    detailScreen: (Int) -> Unit,
    profileScreen: () -> Unit,
    navigateToProductPage: () -> Unit,
    navigateToBasketPage: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.Products)) },
                actions = {
                    IconButton(onClick = profileScreen) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "ПрофильКнопка")
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
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = { detailScreen(product.id) }
                )
            }
        }
    }
}
