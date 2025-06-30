package com.example.week5
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
class Testing {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductsScreen(
        products: List<Product>,
        detailScreen: (Int) -> Unit,
        basketScreen: () -> Unit,
        navigateToProductPage: () -> Unit,
        navigateToBascketPage: () -> Unit
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Продукты") },
                    actions = {
                        IconButton(onClick = { basketScreen() }) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Basket",
                                tint = Color.Black
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                MyBottomAppBar(
                    navigatetoProductPage = navigateToProductPage,
                    navigatetoBascketPage = navigateToBascketPage
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .border(2.dp, color = Color.Black)
            ) {
                items(products) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                        ) {
                        Card(
                            modifier = Modifier
                                .clickable { detailScreen(product.id) },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(0.9f)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(product.photoUri),
                                    contentDescription = "Изображение",
                                    contentScale = ContentScale.Crop
                                )
                                Text(product.title)
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DetailScreen(
        product: Product?,
        addToBascket: () -> Unit,
        backToMainScreen: () -> Unit,
        navigateToProductPage: () -> Unit,
        navigateToBascketPage: () -> Unit
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(
                            onClick = { backToMainScreen() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                MyBottomAppBar(
                    navigatetoProductPage = navigateToProductPage,
                    navigatetoBascketPage = navigateToBascketPage
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = product?.title ?: ""
                )
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product?.photoUri)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .build()
                    ),
                    contentDescription = "Изображение",
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = product?.content ?: ""
                )
                Button(onClick = { addToBascket() }) {
                    Text("Добавить в корзину")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BasketScreen(
        productsForBuy: List<Product>,
        deleteFromBasket: (Int) -> Unit,
        backToMainScreen: () -> Unit,
        navigateToProductPage: () -> Unit,
        navigateToBascketPage: () -> Unit
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Корзина") },
                    navigationIcon = {
                        IconButton(
                            onClick = { backToMainScreen() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                MyBottomAppBar(
                    navigatetoProductPage = navigateToProductPage,
                    navigatetoBascketPage = navigateToBascketPage
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .border(2.dp, color = Color.Black)
            ) {
                items(productsForBuy) { product ->
                    Card(shape = RoundedCornerShape(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(product.photoUri)
                                        .placeholder(R.drawable.ic_launcher_foreground)
                                        .error(R.drawable.ic_launcher_foreground)
                                        .build()
                                ),
                                contentDescription = "Изображение",
                                contentScale = ContentScale.Crop
                            )
                            Text(product.title)
                            IconButton(onClick = { deleteFromBasket(product.id) }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "delete from  basket"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MyBottomAppBar(
        navigatetoProductPage: () -> Unit,
        navigatetoBascketPage: () -> Unit
    ) {
        BottomAppBar(
            actions = {
                IconButton(onClick = { navigatetoProductPage() }) {
                    Icon(Icons.Default.Home, contentDescription = "ProductPage")
                }
                IconButton(onClick = { navigatetoBascketPage() }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "BasketPage")
                }
            }
        )
    }
    @Composable
    fun ProfileScreen(){
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.upside_down_gray_cat),
                    contentDescription = "Аватар",
                    contentScale = ContentScale.Crop
                )
                Text(text = "Ryqite", textAlign = TextAlign.Center)
            }
            Text(text = "Гарбарук Даниил Александрович", textAlign = TextAlign.Center)
        }
    }
}