package com.example.coldstar.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.coldstar.data.remote.responce.Cast
import com.example.coldstar.data.remote.responce.WatchProvider
import com.example.coldstar.model.Film
import com.example.coldstar.model.Genre
import com.example.coldstar.repository.GenreFilmRepository
import com.example.coldstar.repository.HomeRepository
import com.example.coldstar.repository.WatchOttRepository
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: HomeRepository,
    private val genreFilmRepository: GenreFilmRepository,
    private val watchOttRepository: WatchOttRepository
) :
    ViewModel() {
    private val _similarMovie = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val similarMovie :MutableState<Flow<PagingData<Film>>> = _similarMovie

    private val _filmCast = mutableStateOf<List<Cast>>(emptyList())
    val filmCast: State<List<Cast>> = _filmCast

    private val _movieGenreState = mutableStateListOf(Genre(id = null, name = "All"))
    val movieGenreState: SnapshotStateList<Genre> = _movieGenreState

    private val _watchOttState = mutableStateOf<WatchProvider?>(null)
    val watchOttState: MutableState<WatchProvider?> = _watchOttState


    fun getSimilarMovie(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            movieDetailRepository.getSimilarFilm(filmType = filmType, mediaId = filmId).also {
                _similarMovie.value = it
            }.cachedIn(viewModelScope)
        }
    }

    fun getFilmCast(filmType: FilmType, medialId: Int) {
        viewModelScope.launch {
            movieDetailRepository.getFilmCast(filmType = filmType, mediaId = medialId).also {
                if (it is ResourceState.Success) {
                    _filmCast.value = it.data!!.castResult
                }
            }
        }
    }

    fun getFilmTag(filmType: FilmType) {
        viewModelScope.launch {
            when (val chipData = genreFilmRepository.genreFilm(filmType)) {
                is ResourceState.Error -> {
                    Timber.e("You getting Error")
                }
                is ResourceState.Success -> {
                    _movieGenreState.clear()
                    chipData.data?.genres?.forEach {
                        _movieGenreState.add(it)
                    }
                }
                is ResourceState.Loading -> TODO()
            }
        }
    }


    fun watchOttFun(filmType: FilmType, mediaId: Int) {
        viewModelScope.launch {
            watchOttRepository.getWatchOtt(filmType = filmType, mediaId = mediaId).also {
                if (it is ResourceState.Success){
                    _watchOttState.value = it.data!!.results
                }
            }
        }
    }
}