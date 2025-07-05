package com.example.compose_todo.UIcomponents

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Экран добавления новой заметки и редактирования уже существующей
 *
 * @param backToMainPage лямбда-функция для возврата на экран [MainScreen],
 * принимающая содержание заметки в качестве параметра
 * @param deleteNote лямбда-функция для удаления текущей
 * заметки из списка на экране [MainScreen]
 * @param content содержание текущей заметки
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteUI(
    backToMainPage: (String) -> Unit, deleteNote: () -> Unit, content: String = ""
) {
    var noteText by remember { mutableStateOf(content) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            ), title = { Text("") }, navigationIcon = {
                IconButton(onClick = { backToMainPage(noteText) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to main page",
                        tint = Color.White
                    )
                }
            }, actions = {
                IconButton(onClick = { deleteNote() }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }, scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        TextField(value = noteText,
            onValueChange = { noteText = it },
            placeholder = { Text("Введите текст") },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(15.dp)
        )
    }
}