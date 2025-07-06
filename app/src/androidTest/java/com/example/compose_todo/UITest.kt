package com.example.compose_todo

import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.compose_todo.Database.Notes
import com.example.compose_todo.UIcomponents.*
import com.example.compose_todo.ui.theme.ComposetodoTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITest {
    @get:Rule
    val rule = createComposeRule()
    val sample_list = mutableStateListOf(
        Notes(id = 1, content = "hello world", isDone = false),
        Notes(id = 2, content = "bye bye", isDone = true)
    )

    @Test
    fun MainScreenTest() {
        var testId: Int? = null
        var themeChanchedCount = 0
        var isDarkTheme = true
        rule.setContent {
            ComposetodoTheme {
                MainScreen(
                    notes = sample_list,
                    floatingActionButtonLogic = {},
                    transitionToCertainNotePage = {},
                    onCheckedChange = { id, isDone ->
                        testId = id
                        sample_list.replaceAll {
                            if (it.id == id) it.copy(isDone = isDone) else it
                        }
                    },
                    changeTheme = {
                        themeChanchedCount++
                        isDarkTheme = !isDarkTheme
                    })
            }
        }
        rule.onNodeWithTag("Notes").assertIsDisplayed()
        rule.onNodeWithContentDescription("Search note")
            .performClick()
        rule.onNodeWithTag("Search")
            .assertIsDisplayed().performTextInput("hel")
        rule.onNodeWithText("hello world").assertIsDisplayed()
        rule.onNodeWithText("bye bye").assertIsNotDisplayed()
        rule.onNodeWithContentDescription("BackIcon").performClick()
        rule.onNodeWithTag("Notes").assertIsDisplayed()
        rule.onNodeWithText("hello world").assertIsDisplayed()
        val checkboxes = rule.onAllNodesWithTag("Checkbox")
        checkboxes[0].performClick()
        assertEquals(true, sample_list[0].isDone)
        assertEquals(1, testId)
        checkboxes[0].performClick()
        assertEquals(false, sample_list[0].isDone)
        assertEquals(1, testId)
    }

    @Test
    fun NavigationTest() {
        rule.setContent {
            ComposetodoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "main_screen"
                ) {
                    composable("main_screen") {
                        MainScreen(
                            notes = sample_list,
                            floatingActionButtonLogic = {
                                navController.navigate("notePage/new")
                            },
                            transitionToCertainNotePage = { testNoteId ->
                                navController.navigate("notePage/$testNoteId")
                            },
                            onCheckedChange = { _, _ -> },
                            changeTheme = {})
                    }
                    composable(
                        route = "notePage/new", arguments = listOf()
                    ) {
                        NoteUI(
                            backToMainPage = { testNoteContent ->
                                if (testNoteContent.isNotBlank()) {
                                    val newId = sample_list.maxOfOrNull { it.id }?.plus(1) ?: 1
                                    sample_list.add(
                                        Notes(
                                            id = newId,
                                            content = testNoteContent
                                        )
                                    )
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
                        val existingNote = sample_list.find { it.id == noteId }
                        NoteUI(
                            backToMainPage = { testNoteContent ->
                                existingNote?.let { testNote ->
                                    val updateNote = testNote.copy(content = testNoteContent)
                                    val index = sample_list.indexOfFirst { note->note.id == noteId }
                                    sample_list[index] = updateNote
                                }
                                navController.popBackStack()
                            },
                            deleteNote = {
                                existingNote?.let {
                                    sample_list.removeAll { it.id == noteId }
                                }
                                navController.popBackStack()
                            },
                            content = existingNote?.content ?: ""
                        )
                    }
                }
            }
        }
        rule.onNodeWithText("hello world").assertIsDisplayed()
            .performClick()
        rule.onNodeWithText("hello world").assertIsDisplayed()
        rule.onNodeWithTag("EditField").assertIsDisplayed()
            .performTextInput("123")
        rule.onNodeWithText("123hello world").assertIsDisplayed()
        assertEquals(2, sample_list.size)
        rule.onNodeWithContentDescription("BackToMainPage").performClick()
        rule.onNodeWithText("123hello world").assertIsDisplayed().performClick()
        rule.onNodeWithContentDescription("Delete").performClick()
        rule.onNodeWithText("123hello world").assertDoesNotExist()
        rule.onNodeWithText("bye bye").assertIsDisplayed()
        assertEquals(1, sample_list.size)
        rule.onNodeWithContentDescription("Floating Button")
            .assertIsDisplayed().performClick()
        rule.onNodeWithTag("EditField").assertIsDisplayed()
            .performTextInput("New note")
        rule.onNodeWithText("New note").assertIsDisplayed()
        assertEquals(1, sample_list.size)
        rule.onNodeWithContentDescription("BackToMainPage").performClick()
        assertEquals(2, sample_list.size)
        rule.onNodeWithText("New note").assertIsDisplayed().performClick()
        rule.onNodeWithContentDescription("Delete").performClick()
        rule.onNodeWithText("New note").assertDoesNotExist()
        assertEquals(1, sample_list.size)
        rule.onNodeWithText("bye bye").assertIsDisplayed()
    }
}
