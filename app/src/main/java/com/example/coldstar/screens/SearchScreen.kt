package com.example.coldstar.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.coldstar.R
import com.example.coldstar.model.Film
import com.example.coldstar.screens.destinations.DetailFilmScreenDestination
import com.example.coldstar.shareCompose.BackButton
import com.example.coldstar.shareCompose.SearchResultItem
import com.example.coldstar.ui.theme.AppOnPrimaryColor
import com.example.coldstar.ui.theme.ButtonColor
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.Utils.BASE_POSTER_IMAGE_URL
import com.example.coldstar.viewmodel.HomeViewModel
import com.example.coldstar.viewmodel.MultiSearchViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: MultiSearchViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val searchInput: String by remember { mutableStateOf("") }
    val movieData = viewModel.multiSearchState.value.collectAsLazyPagingItems()

    LaunchedEffect(key1 = searchInput) {
        if (viewModel.searchParam.value.trim().isNotEmpty() &&
            viewModel.searchParam.value.trim().length != viewModel.previousSearch.value.length
        ) {
            delay(750)
            viewModel.searchFilm()
            viewModel.previousSearch.value = searchInput.trim()
        }
    }

    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .fillMaxWidth(fraction = 0.60F)
        ) {
            val focusManager = LocalFocusManager.current
            BackButton(modifier = Modifier) {
                focusManager.clearFocus()
                navigator.navigateUp()
            }

            Text(
                text = "Search",
                modifier = Modifier.padding(start = 50.dp),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = AppOnPrimaryColor
            )
        }
        SearchBar(
            viewModel = viewModel,
            autoFocus = true,
            onSearch = { viewModel.searchFilm() }
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            when (movieData.loadState.refresh) {
                is LoadState.NotLoading -> {
                    items(movieData) { film ->
                        val focus = LocalFocusManager.current
                        SearchResultItem(
                            title = film!!.title,
                            mediaType = film.mediaType,
                            posterImage = "$BASE_POSTER_IMAGE_URL/${film.posterPath}",
                            genres = homeViewModel.filmGenre.filter { genre ->
                                return@filter if (film.genreIds.isNullOrEmpty()) false else
                                    film.genreIds.contains(genre.id)
                            },
                            rating = (film.voteAverage ?: 0) as Double,
                            releaseYear = film.releaseDate,
                            onClick = {
                                val navFilm = Film(
                                    adult = film.adult ?: false,
                                    backdropPath = film.backdropPath,
                                    posterPath = film.posterPath,
                                    genreIds = film.genreIds,
                                    genres = film.genres,
                                    mediaType = film.mediaType,
                                    id = film.id ?: 0,
                                    imdbId = film.imdbId,
                                    originalLanguage = film.originalLanguage ?: "",
                                    overview = film.overview ?: "",
                                    popularity = film.popularity ?: 0F.toDouble(),
                                    releaseDate = film.releaseDate ?: "",
                                    runtime = film.runtime,
                                    title = film.title ?: "",
                                    video = film.video ?: false,
                                    voteAverage = film.voteAverage ?: 0F.toDouble(),
                                    voteCount = film.voteCount ?: 0
                                )
                                focus.clearFocus()
                                navigator.navigate(
                                    direction = DetailFilmScreenDestination(
                                        navFilm,
                                        if (navFilm.mediaType == "tv") FilmType.TVSHOW else FilmType.MOVIE
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            })
                    }
                    if (movieData.itemCount == 0) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.no_match_found),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
                LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(60.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Text(text = "Error")
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    autoFocus: Boolean,
    viewModel: MultiSearchViewModel, onSearch: () -> Unit,
) {
    var searchInput by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = searchInput) {
        if (searchInput.trim().isNotEmpty() &&
            searchInput.trim().length != viewModel.previousSearch.value.length
        ) {
            delay(750)
            onSearch()
            viewModel.previousSearch.value = searchInput.trim()
        }
    }

    Box(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            .clip(CircleShape)
            .background(ButtonColor)
            .fillMaxWidth()
            .height(54.dp)
    ) {
        TextField(
            value = searchInput,
            onValueChange = {
                searchInput = if (it.trim().isNotEmpty()) it else ""
                viewModel.searchParam.value = searchInput
            },
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester = focusRequester),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search...",
                    color = AppOnPrimaryColor.copy(alpha = 0.8F)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White.copy(alpha = 0.78F),
                containerColor = Color.Transparent,
                disabledTextColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (viewModel.searchParam.value.trim().isNotEmpty()) {
                        focusManager.clearFocus()
                        viewModel.searchParam.value = searchInput
                        if (searchInput != viewModel.previousSearch.value) {
                            viewModel.previousSearch.value = searchInput
                            onSearch()
                        }
                    }
                }
            ),

            trailingIcon = {
                LaunchedEffect(Unit) {
                    if (autoFocus) {
                        focusRequester.requestFocus()
                    }
                }
                Row {
                    AnimatedVisibility(visible = searchInput.trim().isNotEmpty()) {
                        androidx.compose.material.IconButton(onClick = {

                            focusManager.clearFocus()
                            searchInput = ""
                            viewModel.searchParam.value = ""
                        }) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.Clear,
                                tint = AppOnPrimaryColor,
                                contentDescription = null
                            )
                        }
                    }

                    IconButton(onClick = {
                        if (viewModel.searchParam.value.trim().isNotEmpty()) {
                            focusManager.clearFocus()
                            viewModel.searchParam.value = searchInput
                            if (searchInput != viewModel.previousSearch.value) {
                                viewModel.previousSearch.value = searchInput
                                onSearch()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = AppOnPrimaryColor,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}
