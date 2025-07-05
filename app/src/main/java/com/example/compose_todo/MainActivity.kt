package com.example.compose_todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val DARK_THEME = booleanPreferencesKey("dark_theme")
    private val viewModel by viewModels<NotesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkThemeState = dataStore.data
                .map { preferences ->
                    preferences[DARK_THEME] ?: true }
                .collectAsState(initial = true)
            ComposetodoTheme(darkTheme = darkThemeState.value) {
                val notes by viewModel.getAllNotes().collectAsState(initial = emptyList())
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                SideEffect {
                    Log.d("Theme","Success theme change")
                }
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
                        },
                            changeTheme = {
                            coroutineScope.launch {
                                dataStore.edit { settings->
                                    val currentState = settings[DARK_THEME] ?: true
                                    settings[DARK_THEME] = !currentState
                                }
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