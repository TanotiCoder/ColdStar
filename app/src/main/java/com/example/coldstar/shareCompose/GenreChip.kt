package com.example.coldstar.shareCompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coldstar.ui.theme.ButtonColor

@Composable
fun GenreChip(title: String, onClick: () -> Unit, selected: Boolean) {
    Box(modifier = Modifier
        .clickable { onClick() }
        .clip(MaterialTheme.shapes.large)
        .background(if (selected) Color.White else ButtonColor)) {
        Text(
            text = title,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            color = if (selected) ButtonColor else Color.White
        )
    }
}


@Composable
fun DetailGenreChip(title: String) {
    Box(modifier = Modifier
        .clip(MaterialTheme.shapes.large)
        .background(ButtonColor)) {
        Text(
            text = title,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}