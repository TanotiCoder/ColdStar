package com.example.coldstar.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coldstar.shareCompose.BackButton
import com.example.coldstar.shareCompose.CircleCoilImage
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.Utils.BASE_POSTER_IMAGE_URL
import com.example.coldstar.viewmodel.MovieDetailViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun WatchOttScreen(
    navigator: DestinationsNavigator,
    filmType: FilmType,
    mediaId: Int,
    title: String,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val ottList = viewModel.watchOttState.value
    val filmId by remember { mutableStateOf(mediaId) }
    val filmType by remember { mutableStateOf(filmType) }
    val filmTitle by remember { mutableStateOf(title) }
    LaunchedEffect(key1 = mediaId) {
        viewModel.watchOttFun(filmType, filmId)
    }

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Row(Modifier.fillMaxWidth()) {
            BackButton(modifier = Modifier) {
                navigator.navigateUp()
            }
            Text(
                text = "$filmTitle \n is Available on",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            columns = GridCells.Fixed(3)
        ) {
            ottList?.provider?.rent?.forEach { rent ->
                item {
                    CircleCoilImage(
                        name = rent.providerName,
                        url = "${BASE_POSTER_IMAGE_URL}/${rent.logoPath}"
                    )
                }
            }
        }
    }
}