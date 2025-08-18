package com.example.week12.Presentation.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.week12.Presentation.Models.ProfileData
import com.example.week12.Presentation.ViewModels.DatabaseViewModel
import com.example.week12.R

/**
 * Экран профиля отображающий информацию о пользователе (пока что просто заглушка)
 *
 * @param backIcon лямбда-функция для возврата на экран [MainScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    backIcon: () -> Unit,
    currentUser: ProfileData?,
    insertNewUser: (ProfileData) -> Unit,
    loginUser: (String,String)->Boolean,
    logoutUser: () -> Unit
) {
    var showCreateProfile by remember { mutableStateOf(false) }
    var showLoginProfile by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.ProfileTitle),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backIcon()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "BackFromProfile"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                currentUser != null -> {
                    AuthenticatedProfileView(currentUser, logoutUser = logoutUser)
                }
                showCreateProfile -> {
                    CreateProfileForm(
                        onBack = { showCreateProfile = false },
                        onSubmit = {profileData ->
                            insertNewUser(profileData)
                            showCreateProfile = false
                        }
                    )
                }
                showLoginProfile -> {
                    LoginProfileForm(
                        onBack = {
                            showLoginProfile = false
                            loginError = false
                        },
                        onSubmit = {nickname,password->
                            if(loginUser(nickname,password)){
                                showLoginProfile = false
                                loginError = false
                            }
                            else {
                                loginError = true
                            }
                        },
                        error = loginError
                    )
                }
                else -> {
                    UnauthenticatedProfileView(
                        onCreateClick = { showCreateProfile = true },
                        onLoginClick = { showLoginProfile = true }
                    )
                }
            }
        }
    }
}

@Composable
private fun CreateProfileForm(
    onBack: () -> Unit,
    onSubmit: (ProfileData) -> Unit
) {
    var nickname by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
        }
        OutlinedTextField(
            value = image,
            onValueChange = { image = it },
            label = { Text("Картинка") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname= it },
            label = { Text("Никнейм") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Полное имя") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(
            onClick = { onSubmit(ProfileData(img = image,nickName = nickname,fullName = fullName,password = password))},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Создать профиль")
        }
    }
}

@Composable
private fun LoginProfileForm(
    onBack: () -> Unit,
    onSubmit: (String,String) -> Unit,
    error: Boolean
) {
    var nickname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
        }
        if (error) {
            Text(
                text = "Неверный логин или пароль",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Nickname") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(
            onClick = { onSubmit(nickname, password) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Войти")
        }
    }
}
@Composable
private fun AuthenticatedProfileView(data: ProfileData,logoutUser:()->Unit) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .size(150.dp)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = data.img,
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
    Text(
        text = data.nickName,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .testTag("Nickname")
    )
    Text(
        text = data.fullName,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        modifier = Modifier
            .padding(bottom = 24.dp)
            .testTag("FullName")
    )
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
    Button(onClick = logoutUser,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Text("Выйти")
    }
}
@Composable
private fun UnauthenticatedProfileView(
    onCreateClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.NotAuthenticated),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.LoginProfile))
        }
        Button(
            onClick = onCreateClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.CreateProfile))
        }
    }
}