package com.example.compose_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Transition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose_todo.ui.theme.ComposetodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposetodoTheme(darkTheme = true) {
                val notes = remember { mutableStateListOf<Notes>() }
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "main"
                )
                {
                    composable("main") {
                        MyUI(notes = notes,
                            searchNote = {},
                            floatingActionButtonLogic = {
                                navController.navigate("notePage/new")
                            },
                            transitionToCertainNotePage = { noteId ->
                                navController.navigate("notePage/$noteId")
                            })
                    }
                    composable(
                        route = "notePage/new",
                        arguments = listOf()
                    ) {
                        NoteUI(backToMainPage = { noteContent ->
                            if (noteContent.isNotBlank()) {
                                val newId = notes.maxOfOrNull { it.id }?.plus(1) ?: 1
                                notes.add(Notes(id = newId, content = noteContent))
                            }
                            navController.popBackStack()
                        },
                            deleteNote = {})
                    }
                    composable(
                        route = "notePage/{noteId}",
                        arguments = listOf(
                            navArgument("noteId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                        val existingNote = notes.find { it.id == noteId }
                        NoteUI(
                            backToMainPage = { noteContent ->
                                notes.replaceAll { note ->
                                    if (note.id == noteId) note.copy(content = noteContent) else note
                                }
                                navController.popBackStack()
                            },
                            deleteNote = {
                                notes.removeAll { it.id == noteId}
                                navController.popBackStack()
                            },
                            content = existingNote?.content ?: ""
                        )
                    }
                }
            }
        }
    }


    // search и check box
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyUI(
        notes: List<Notes>,
        searchNote: () -> Unit,
        floatingActionButtonLogic: () -> Unit,
        transitionToCertainNotePage: (Int) -> Unit
    ) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black, titleContentColor = Color.Yellow
                    ),
                    title = { Text("ЗАМЕТКИ") },
                    navigationIcon = {
                        IconButton(onClick = { searchNote() }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search note",
                                tint = Color.White
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    floatingActionButtonLogic()
                }, modifier = Modifier.size(50.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Floating Button",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(15.dp)
            ) {
                items(notes) { note ->
                    NoteCard(
                        note = note,
                        transitionToCertainNotePage = transitionToCertainNotePage
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

    @Composable
    fun NoteCard(note: Notes, transitionToCertainNotePage: (Int) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Card(
                modifier = Modifier
                    .clickable { transitionToCertainNotePage(note.id) },
                shape = MaterialTheme.shapes.large
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = note.content,
                        modifier = Modifier
                            .align(CenterStart)
                    )
                }
            }
            Checkbox(
                checked = note.isDone,
                onCheckedChange = { note.isDone = it })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteUI(
        backToMainPage: (String) -> Unit,
        deleteNote: () -> Unit,
        content: String = ""
    ) {
        var noteText by remember { mutableStateOf(content) }
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black
                    ),
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = { backToMainPage(noteText) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back to main page",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { deleteNote() }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { innerPadding ->
            TextField(
                value = noteText,
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