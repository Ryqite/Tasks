package com.example.week5

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.week5.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    products: List<Product>,
    detailScreen: (Int) -> Unit,
    profileScreen: () -> Unit,
    navigateToProductPage: () -> Unit,
    navigateToBascketPage: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Продукты") },
                actions = {
                    IconButton(onClick = profileScreen) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "ПрофильКнопка")
                    }
                }
            )
        },
        bottomBar = {
            SimpleBottomBar(
                navigateToProductPage = navigateToProductPage,
                navigateToBascketPage = navigateToBascketPage
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                SimpleProductCard(
                    product = product,
                    onClick = { detailScreen(product.id) }
                )
            }
        }
    }
}

@Composable
fun SimpleProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = product.photoUri,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    product: Product?,
    addToBascket: () -> Unit,
    backIcon: () -> Unit,
    navigateToProductPage: () -> Unit,
    navigateToBascketPage: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали") },
                navigationIcon = {
                    IconButton(onClick = backIcon) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "НазадИзДеталей")
                    }
                }
            )
        },
        bottomBar = {
            SimpleBottomBar(
                navigateToProductPage = navigateToProductPage,
                navigateToBascketPage = navigateToBascketPage
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
                    Text("Добавить в корзину")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    productsForBuy: List<Product>,
    deleteFromBasket: (Int) -> Unit,
    backIcon: () -> Unit,
    navigateToProductPage: () -> Unit,
    navigateToBascketPage: () -> Unit
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
            SimpleBottomBar(
                navigateToProductPage = navigateToProductPage,
                navigateToBascketPage = navigateToBascketPage
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
                    SimpleBasketItem(
                        product = product,
                        onDelete = { deleteFromBasket(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleBasketItem(
    product: Product,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.photoUri,
            contentDescription = product.title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(product.title)
            Text(
                text = product.content.take(30) + if (product.content.length > 30) "..." else "",
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "УдалитьКнопка")
        }
    }
    HorizontalDivider()
}

@Composable
fun SimpleBottomBar(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(backIcon: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("") },
            navigationIcon = {
                IconButton(onClick = backIcon) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "НазадИзПрофиля")
                }
            })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.upside_down_gray_cat),
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Ryqite",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Гарбарук Даниил Александрович",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}