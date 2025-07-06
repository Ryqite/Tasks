package com.example.compose_todo.UIcomponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.compose_todo.Database.Notes

/**
 * Главный экран приложения, отображающий список всех заметок
 *
 * @param notes список заметок для этображения
 * @param floatingActionButtonLogic лямбда-функция для перехода на
 * экран [NoteUI] с целью создания новой заметки
 * @param transitionToCertainNotePage лямбда-функция для перехода в [NoteUI]
 * с целью редактирования заметки, id которой принимается в качестве параметра
 * @param onCheckedChange лямбда-функция для изменения текущего статуса чекбокса,
 * принимающая id заметки, чекбокс которой надо изменить, и новое значение чекбокса
 * @param changeTheme лямбда-функция для изменения текущей темы на противоположную
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    notes: List<Notes>,
    floatingActionButtonLogic: () -> Unit,
    transitionToCertainNotePage: (Int) -> Unit,
    onCheckedChange: (Int, Boolean) -> Unit,
    changeTheme: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val filteredNotes = remember(notes, searchQuery) {
        if (searchQuery.isEmpty()) notes else notes.filter {
            it.content.contains(
                searchQuery, ignoreCase = true
            )
        }
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(modifier = Modifier, topBar = {
        if (isSearching) {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            ), title = {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Поиск...", color = Color.White
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                        .testTag("Search"),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    isSearching = false
                    searchQuery = ""
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "BackIcon",
                        tint = Color.White
                    )
                }
            },
                scrollBehavior = scrollBehavior
            )
        } else {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black, titleContentColor = Color.Yellow
            ), title = {
                Text(
                    modifier = Modifier.testTag("Notes"), text = "ЗАМЕТКИ"
                )
            }, navigationIcon = {
                IconButton(onClick = { isSearching = true }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search note",
                        tint = Color.White
                    )
                }
            },
                actions = {
                    IconButton(onClick = changeTheme) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "themeIcon",
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    }, floatingActionButton = {
        if (!isSearching) {
            FloatingActionButton(
                onClick = { floatingActionButtonLogic() }, modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Floating Button",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp)
        ) {
            items(filteredNotes) { note ->
                NoteCard(
                    note = note,
                    onCheckedChange = onCheckedChange,
                    transitionToCertainNotePage = transitionToCertainNotePage
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}