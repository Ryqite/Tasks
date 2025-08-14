package com.example.week5.UIcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.week5.Data.Product
import com.example.week5.R

/**
 * Экран определенного продукта, id которого было переданно из [ProductsScreen]
 *
 * @param product продукт который будет отображаться
 * @param addToBascket лябда-функция для добавления текущего продукта в корзину продуктов
 * экрана [BasketScreen]
 * @param backIcon лябда-функция для возврата на экран [ProductsScreen]
 * @param navigateToProductPage лямбда-функция,передающаяся в [BottomBar] для перехода
 * на [ProductsScreen]
 * @param navigateToBasketPage лямбда-функция,передающаяся в [BottomBar] для перехода
 * на [BasketScreen]
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    product: Product?,
    addToBascket: () -> Unit,
    backIcon: () -> Unit,
    navigateToProductPage: () -> Unit,
    navigateToBasketPage: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.Details), modifier =
                Modifier.testTag("Details"))},

                navigationIcon = {
                    IconButton(onClick = backIcon) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "BackFromDetails")
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            AsyncImage(
                model = product?.photoUri,
                contentDescription = product?.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product?.title ?: "",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product?.content ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = addToBascket,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.AddButton))
                }
            }
        }
    }
}
