package com.example.coldstar.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coldstar.shareCompose.BackButton
import com.example.coldstar.shareCompose.SearchResultItem
import com.example.coldstar.shareCompose.SwipeToDismissItem
import com.example.coldstar.utlis.Utils.BASE_POSTER_IMAGE_URL
import com.example.coldstar.viewmodel.WatchListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SavedMovieScreen(
    watchListViewModel: WatchListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val roomData = watchListViewModel.myMovieData.value.collectAsState(initial = emptyList()).value

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            //Text(text = roomData.size.toString())
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BackButton(modifier = Modifier) {
                    navigator.navigateUp()
                }
                Text(
                    text = "You have ${roomData.size}",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }
        }
        items(roomData, key = { it.mediaId }) {
            SwipeToDismissItem(
                modifier = Modifier,
                onDismiss = { watchListViewModel.removeFromWatchList(it.mediaId) }) {
                SearchResultItem(
                    title = it.title,
                    mediaType = null,
                    posterImage = "${BASE_POSTER_IMAGE_URL}/${it.imagePath}",
                    genres = emptyList(),
                    rating = it.rating,
                    releaseYear = it.releaseDate
                ) {

                }
            }
        }
    }
}