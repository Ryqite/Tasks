package com.example.week9.Presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week9.Presentation.Data.ProfileData
import com.example.week9.R

/**
 * Экран профиля отображающий информацию о пользователе (пока что просто заглушка)
 *
 * @param backIcon лямбда-функция для возврата на экран [ProductsScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    data: ProfileData,
    backIcon: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.ProfileTitle)) },
            navigationIcon = {
                IconButton(onClick = backIcon) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "BackFromProfile"
                    )
                }
            })
    }
    ) { innerPadding ->
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
                    painter = painterResource(id = data.img),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = data.nickName,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = data.fullName,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}