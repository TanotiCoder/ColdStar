package com.example.coldstar.shareCompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.coldstar.model.Genre
import com.example.coldstar.ui.theme.ButtonColor

@Composable
fun SearchResultItem(
    title: String?,
    mediaType: String?,
    posterImage: String?,
    genres: List<Genre>?,
    rating: Double,
    releaseYear: String?,
    onClick: () -> Unit?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            )
            .height(130.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ButtonColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PosterCoilImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(86.5.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp)),
                url = posterImage ?: "N/A"
            )
            Column(
                Modifier.fillMaxHeight().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                DetailGenreChip(
                    title = when (mediaType) {
                        "tv" -> "Tv Show"
                        "movie" -> "Movie"
                        else -> ""
                    }
                )
                Text(
                    text = title ?: "N/A",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = releaseYear ?: "N/A",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall
                )
                RatingBarUI(vote = rating)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    genres?.forEach {
                        item {
                            DetailGenreChip(title = it.name)
                        }
                    }
                }
            }
        }
    }
}