package com.example.week12.Presentation.UIComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.week12.Domain.Models.AppTheme
import com.example.week12.Presentation.Models.BooksDatabaseItem
import com.example.week12.Presentation.ViewModels.DatabaseViewModel
import com.example.week12.R


@Composable
fun SavedBookItemCard(
    viewModel: DatabaseViewModel,
    book: BooksDatabaseItem,
    onFilmsItemClick: (String) -> Unit,
    theme: AppTheme
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .testTag("Card")
            .clickable(onClick = { onFilmsItemClick(book.title) }),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = book.image.replace("http://", "https://"),
                contentDescription = "ImageBooksItem",
                modifier = Modifier
                    .size(160.dp, 90.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 8.dp).weight(1f)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = when (theme) {
                        AppTheme.DARK -> Color.LightGray
                        AppTheme.LIGHT -> Color.DarkGray
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.Rating),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = when (theme) {
                            AppTheme.DARK -> Color.LightGray
                            AppTheme.LIGHT -> Color.DarkGray
                        }
                    )
                    Text(
                        text = book.rating.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = when (theme) {
                            AppTheme.DARK -> Color.LightGray
                            AppTheme.LIGHT -> Color.DarkGray
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.PublishedAt),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = when (theme) {
                            AppTheme.DARK -> Color.LightGray
                            AppTheme.LIGHT -> Color.DarkGray
                        }
                    )
                    Text(
                        text = book.publishedAt,
                        style = MaterialTheme.typography.labelSmall,
                        color = when (theme) {
                            AppTheme.DARK -> Color.LightGray
                            AppTheme.LIGHT -> Color.DarkGray
                        }
                    )
                }
            }
            IconButton(
                onClick = { viewModel.deleteBook(book) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "DeleteIcon")

            }
        }
    }
}