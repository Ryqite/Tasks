package com.example.compose_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose_todo.Database.Notes
import com.example.compose_todo.Database.NotesDatabase
import com.example.compose_todo.UIcomponents.MainScreen
import com.example.compose_todo.UIcomponents.NoteUI
import com.example.compose_todo.ui.theme.ComposetodoTheme


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<NotesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposetodoTheme(darkTheme = true) {
                val notes by viewModel.getAllNotes().collectAsState(initial = emptyList())
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
                            notes.find {it.id == noteId}?.let {note->
                                viewModel.updateNote(note.copy(isDone = isChecked))
                            }
                        })
                    }
                    composable(
                        route = "notePage/new", arguments = listOf()
                    ) {
                        NoteUI(backToMainPage = { noteContent ->
                            if (noteContent.isNotBlank()) {
                                val newId = notes.maxOfOrNull { it.id }?.plus(1) ?: 1
                               viewModel.insertNote(Notes(id = newId, content = noteContent))
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
                            existingNote?.let {
                                viewModel.updateNote(it.copy(content = noteContent))
                            }
                            navController.popBackStack()
                        }, deleteNote = {
                            existingNote?.let {
                                viewModel.deleteNote(it)
                            }
                            navController.popBackStack()
                        }, content = existingNote?.content ?: ""
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TestPreview() {

    }
}