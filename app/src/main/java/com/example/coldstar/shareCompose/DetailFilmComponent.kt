package com.example.coldstar.shareCompose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun IconRow(
    exist: Int,
    context: Context,
    removeOnClick: () -> Unit,
    addOnClick: () -> Unit,
    onReviewClick: () -> Unit,
    ottOnClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        IconButton(onClick = { onReviewClick() }) {
            Icon(imageVector = Icons.Default.RateReview, contentDescription = "Review")
        }
        IconButton(onClick = { ottOnClick() }) {
            Icon(
                imageVector = Icons.Default.PlayCircleOutline,
                contentDescription = "Play"
            )
        }
        IconButton(onClick = {
            if (exist != 0) {
                removeOnClick()
                Toast.makeText(
                    context,
                    "Remove From your Watch List",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                addOnClick()
                Toast.makeText(
                    context,
                    "Added to your Watch List",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Icon(
                imageVector = if (exist != 0) {
                    Icons.Default.Done
                } else {
                    Icons.Default.AddCircleOutline
                },
                contentDescription = "Review"
            )
        }
    }
}
