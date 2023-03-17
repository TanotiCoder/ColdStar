package com.example.coldstar.shareCompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coldstar.ui.theme.TranslucentAppPrimaryColor
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun RatingBarUI(vote: Double) {
    RatingBar(
        value = (vote / 2).toFloat(),
        onValueChange = {},
        onRatingChanged = {},
        config = RatingBarConfig()
            .style(RatingBarStyle.Normal)
            .isIndicator(true)
            .activeColor(Color.Yellow)
            .hideInactiveStars(false)
            .inactiveColor(Color.Gray)
            .stepSize(StepSize.HALF)
            .numStars(5)
            .size(16.dp)
            .padding(4.dp)
    )
}

@Composable
fun BackButton(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(MaterialTheme.shapes.large)
            .background(TranslucentAppPrimaryColor)
    ) {
        IconButton(onClick = { onClick() }, modifier = Modifier.fillMaxSize()) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
    }
}

@Composable
fun ShowAboutCategory(title: String, description: String) {
    var iconPos by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp),
        )
        IconButton(onClick = { iconPos = !iconPos }) {
            Icon(
                imageVector = if (iconPos) Icons.Default.ArrowDropUp else Icons.Default.Info,
                contentDescription = "Button"
            )
        }
    }
    AnimatedVisibility(visible = iconPos) {
        Box(
            Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .border(1.dp, Color.White)
                .clip(MaterialTheme.shapes.large)
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}