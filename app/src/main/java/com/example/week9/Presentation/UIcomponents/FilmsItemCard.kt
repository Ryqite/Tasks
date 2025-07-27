package com.example.week9.Presentation.UIcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.week9.Presentation.FilmsItem
import com.example.week9.R

/**
 * Карточка для отображения новостей в списке на экране [MainScreen]
 *
 * @param films Текущая новость, для которой будет создаваться внешний вид
 * @param onfilmsItemClick  обработчик нажатия на заметку, принимающий
 * [title] заметки в качестве параметра
 */
@Composable
fun FilmsItemCard(films: FilmsItem, onFilmsItemClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .testTag("Card")
            .clickable(onClick = { onFilmsItemClick(films.kinopoiskId) }),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = films.imageURL,
                contentDescription = "ImagefilmsItem",
                modifier = Modifier
                    .size(160.dp, 90.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = films.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.Rating),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                    Text(
                        text = films.rating,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.KinopoiskID),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                    Text(
                        text = films.kinopoiskId.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black
                    )
                }
            }
        }
    }
}