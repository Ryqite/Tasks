package com.example.week7.UIcomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.week7.Data.News
import com.example.week7.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    certainNews: News?,
    navigateToMainScreen:()->Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { certainNews?.source?.let { Text(it) } },
                navigationIcon = {
                    IconButton(onClick = navigateToMainScreen ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
//        bottomBar = {
//            Button(
//                onClick = { TODO()},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp)
//                    .height(60.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Gray
//                )
//            ) {
//                Text(
//                    text = "В закладки",
//                    fontSize = 24.sp,
//                    color = Color.White
//                )
//            }
//        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = certainNews?.imageURL,
                contentDescription = "PictureDetailScreen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                certainNews?.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                certainNews?.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}