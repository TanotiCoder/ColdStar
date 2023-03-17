package com.example.coldstar.shareCompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coldstar.ui.theme.ButtonColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissItem(
    modifier: Modifier,
    onDismiss: () -> Unit,
    swappable: @Composable () -> Unit
) {

    val dismissState = rememberDismissState(initialValue = DismissValue.Default,
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismiss()
            }
            true
        })

    SwipeToDismiss(
        state = dismissState,
        modifier = modifier,
        background = {
            if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                Box(
                    modifier = androidx.compose.ui.Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, ButtonColor.copy(alpha = 0.25F))
                            )
                        )
                        .padding(8.dp)
                ) {
                    Column(modifier = androidx.compose.ui.Modifier.align(Alignment.CenterEnd)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = androidx.compose.ui.Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        },
        dismissContent = {
            swappable()
        },
        directions = setOf(DismissDirection.EndToStart)
    )
}