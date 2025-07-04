package com.example.week5.UIcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Нижняя панель навигации в приложении
 *
 * @param navigateToProductPage лямбда-функция для перехода
 * на [ProductsScreen]
 * @param navigateToBascketPage лямбда-функция для перехода
 * на [BasketScreen]
 */
@Composable
fun BottomBar(
    navigateToProductPage: () -> Unit,
    navigateToBascketPage: () -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = navigateToProductPage) {
                Icon(Icons.Filled.Home, contentDescription = "ПродуктыКнопка")
            }
            IconButton(onClick = navigateToBascketPage) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "КорзинаКнопка")
            }
        }
    }
}