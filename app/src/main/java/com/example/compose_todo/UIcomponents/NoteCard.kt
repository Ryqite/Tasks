package com.example.compose_todo.UIcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.compose_todo.Database.Notes

/**
 * Карточка для отображения заметок в списке на экране [MainScreen]
 *
 * @param note текущая заметка, для которой будет создаваться внешний вид
 * @param onCheckedChange обработчик изменения значения чекбокса,
 * принимающий id заметки, чекбокс которой надо изменить, и новое значение чекбокса
 * @param transitionToCertainNotePage обработчик нажатия на заметку, принимающий
 * id заметки в качестве параметра
 */
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
            colors = CardDefaults.cardColors (
                containerColor = if(note.isDone) Color.DarkGray else Color.Gray),
            shape = MaterialTheme.shapes.large,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = note.content, modifier = Modifier.align(CenterStart)
                        .testTag("NoteCard"),
                    color = if (note.isDone) Color.Gray else Color.White
                )
            }
        }
        Checkbox(modifier = Modifier.testTag("Checkbox"),
            checked = note.isDone, onCheckedChange = { newValue ->
            onCheckedChange(note.id, newValue)
        })
    }
}
