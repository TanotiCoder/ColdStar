package com.example.coldstar.shareCompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.coldstar.R
import com.example.coldstar.screens.destinations.DetailFilmScreenDestination
import com.example.coldstar.model.Film
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.Utils.BASE_BACKDROP_IMAGE_URL
import com.example.coldstar.utlis.Utils.BASE_POSTER_IMAGE_URL
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

//The application may be doing too much work on its main thread.
//Skipped 103 frames!  The application may be doing too much work on its main thread.
@Composable
fun ScrollableMovieItem(
    pagingData: LazyPagingItems<Film>,
    landscape: Boolean,
    retryOnClick: () -> Unit,
    navigator: DestinationsNavigator,
    filmType: FilmType
) {

    when (pagingData.loadState.refresh) {
        is LoadState.NotLoading -> {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pagingData) {
                    CoilImageUI(
                        modifier = Modifier
                            .width(if (landscape) 215.dp else 130.dp)
                            .height(if (landscape) 161.dp else 195.dp),
                        landscape = landscape,
                        title = it!!.title,
                        imgUrl = if (landscape)
                            "$BASE_BACKDROP_IMAGE_URL/${it.backdropPath}"
                        else
                            "$BASE_POSTER_IMAGE_URL/${it.posterPath}",
                        onClick = {
                            navigator.navigate(
                                direction = DetailFilmScreenDestination(
                                    it,
                                    filmType
                                )
                            )
                        }
                    )
                }
            }
        }
        LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //.width(if (landscape) 215.dp else 130.dp)
                    .height(if (landscape) 161.dp else 195.dp),
                contentAlignment = Alignment.Center
            ) {
                LoopReverseLottieLoading(lottieFile = R.raw.loding_see_saw)
            }
        }
        is LoadState.Error -> {
            Box(
                Modifier
                    .clickable { retryOnClick() }
                    .fillMaxWidth()
                    .height(if (landscape) 161.dp else 195.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error,Tap to Retry")
            }
        }
    }
}