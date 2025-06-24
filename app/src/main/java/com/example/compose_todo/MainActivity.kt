package com.example.compose_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose_todo.ui.theme.ComposetodoTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposetodoTheme(darkTheme = true) {
                val notes = remember { mutableStateListOf<Notes>() }
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(notes = notes, floatingActionButtonLogic = {
                            navController.navigate("notePage/new")
                        }, transitionToCertainNotePage = { noteId ->
                            navController.navigate("notePage/$noteId")
                        }, onCheckedChange = { noteId, isChecked ->
                            notes.replaceAll { currentNote ->
                                if (currentNote.id == noteId) currentNote.copy(isDone = isChecked)
                                else currentNote
                            }
                        })
                    }
                    composable(
                        route = "notePage/new", arguments = listOf()
                    ) {
                        NoteUI(backToMainPage = { noteContent ->
                            if (noteContent.isNotBlank()) {
                                val newId = notes.maxOfOrNull { it.id }?.plus(1) ?: 1
                                notes.add(Notes(id = newId, content = noteContent))
                            }
                            navController.popBackStack()
                        }, deleteNote = {
                            navController.popBackStack()
                        })
                    }
                    composable(
                        route = "notePage/{noteId}", arguments = listOf(navArgument("noteId") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                        val existingNote = notes.find { it.id == noteId }
                        NoteUI(backToMainPage = { noteContent ->
                            notes.replaceAll { note ->
                                if (note.id == noteId) note.copy(content = noteContent) else note
                            }
                            navController.popBackStack()
                        }, deleteNote = {
                            notes.removeAll { it.id == noteId }
                            navController.popBackStack()
                        }, content = existingNote?.content ?: ""
                        )
                    }
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        notes: List<Notes>,
        floatingActionButtonLogic: () -> Unit,
        transitionToCertainNotePage: (Int) -> Unit,
        onCheckedChange: (Int, Boolean) -> Unit
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
                    TextField(value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Поиск...", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
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
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }, scrollBehavior = scrollBehavior
                )
            } else {
                CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black, titleContentColor = Color.Yellow
                ), title = { Text("ЗАМЕТКИ") }, navigationIcon = {
                    IconButton(onClick = { isSearching = true }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search note",
                            tint = Color.White
                        )
                    }
                }, scrollBehavior = scrollBehavior
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
    @Composable
    fun NoteCard(
        note: Notes,
        onCheckedChange: (Int, Boolean) -> Unit,
        transitionToCertainNotePage: (Int) -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Card(
                modifier = Modifier
                    .clickable { transitionToCertainNotePage(note.id) },
                shape = MaterialTheme.shapes.large,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = note.content, modifier = Modifier.align(CenterStart)
                    )
                }
            }
            Checkbox(checked = note.isDone, onCheckedChange = { newValue ->
                onCheckedChange(note.id, newValue)
            })
        }
    }

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
    @Preview(showBackground = true)
    @Composable
    fun TestPreview() {

    }
}