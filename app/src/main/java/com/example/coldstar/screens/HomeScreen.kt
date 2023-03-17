package com.example.coldstar.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.coldstar.screens.destinations.SavedMovieScreenDestination
import com.example.coldstar.screens.destinations.SearchScreenDestination
import com.example.coldstar.shareCompose.GenreChip
import com.example.coldstar.shareCompose.ScrollableMovieItem
import com.example.coldstar.shareCompose.SearchAndList
import com.example.coldstar.shareCompose.ShowAboutCategory
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.viewmodel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), navigator: DestinationsNavigator) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SearchAndList(
            homeViewModel = homeViewModel,
            onRoomWatchList = { navigator.navigate(direction = SavedMovieScreenDestination()) },
            onSearchScreen = { navigator.navigate(direction = SearchScreenDestination()) }
        )
        NestedScroll(homeViewModel, navigator = navigator)
    }
}

@Composable
fun NestedScroll(homeViewModel: HomeViewModel, navigator: DestinationsNavigator) {
    val trendingFilm = homeViewModel.trendingFilm.value.collectAsLazyPagingItems()
    val popularFilm = homeViewModel.popularFilm.value.collectAsLazyPagingItems()
    val topRatedFilm = homeViewModel.topRatedFilm.value.collectAsLazyPagingItems()
    val nowPlayingFilm = homeViewModel.nowPlayingFilm.value.collectAsLazyPagingItems()
    val upComingFilm = homeViewModel.upComingFilm.value.collectAsLazyPagingItems()
    val backInDaysFilm = homeViewModel.backInDaysFilm.value.collectAsLazyPagingItems()
    val genreChip = homeViewModel.filmGenre
    val filmType: FilmType = homeViewModel.selectedFilmType.value

    LazyColumn(Modifier.fillMaxSize()) {

        item {
            Text(
                text = "Genre",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
        item {
            LazyRow(
                Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(genreChip) { genreChip ->
                    GenreChip(
                        title = genreChip.name,
                        onClick = {
                            if (homeViewModel.selectedGenre.value != genreChip) {
                                homeViewModel.selectedGenre.value = genreChip
                                homeViewModel.hitNetworkCall()
                            }
                        },
                        selected = genreChip.name == homeViewModel.selectedGenre.value.name
                    )
                }
            }
        }
        item {
            Text(
                text = "Trending",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
        item {
            ScrollableMovieItem(
                pagingData = trendingFilm,
                landscape = true,
                retryOnClick = homeViewModel::hitNetworkCall,
                navigator = navigator,
                filmType = filmType
            )
        }
        item {
            Text(
                text = "Popular",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
        item {
            ScrollableMovieItem(
                pagingData = popularFilm,
                landscape = false,
                retryOnClick = homeViewModel::hitNetworkCall,
                navigator = navigator,
                filmType = filmType
            )
        }
        item {
            Text(
                text = "Top Rated",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
        item {
            ScrollableMovieItem(
                pagingData = topRatedFilm,
                landscape = false,
                retryOnClick = homeViewModel::hitNetworkCall,
                navigator = navigator,
                filmType = filmType
            )
        }
        item {
            Text(
                text = "Now Playing",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
        item {
            ScrollableMovieItem(
                pagingData = nowPlayingFilm,
                landscape = false,
                retryOnClick = homeViewModel::hitNetworkCall,
                navigator = navigator,
                filmType = filmType
            )
        }
        item {
            Text(
                text = "Upcoming",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
        item {
            ScrollableMovieItem(
                pagingData = upComingFilm,
                landscape = false,
                retryOnClick = homeViewModel::hitNetworkCall,
                navigator = navigator,
                filmType = filmType
            )
        }
        item {
            ShowAboutCategory(
                title = "Back In Days",
                description = "Films released between 1940 and 1980"
            )
        }
        item {
            ScrollableMovieItem(
                pagingData = backInDaysFilm,
                landscape = false,
                retryOnClick = homeViewModel::hitNetworkCall,
                navigator = navigator,
                filmType = filmType
            )
        }
    }
}