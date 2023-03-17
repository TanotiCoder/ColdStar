package com.example.coldstar.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.coldstar.model.Film
import com.example.coldstar.model.Genre
import com.example.coldstar.repository.GenreFilmRepository
import com.example.coldstar.repository.HomeRepository
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val genreFilmRepository: GenreFilmRepository
) : ViewModel() {

    private val _trendingFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val trendingFilm: MutableState<Flow<PagingData<Film>>> = _trendingFilm

    private val _popularFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val popularFilm: MutableState<Flow<PagingData<Film>>> = _popularFilm

    private val _topRatedFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val topRatedFilm: MutableState<Flow<PagingData<Film>>> = _topRatedFilm

    private val _nowPlayingFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val nowPlayingFilm: MutableState<Flow<PagingData<Film>>> = _nowPlayingFilm

    private val _upComingFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val upComingFilm: MutableState<Flow<PagingData<Film>>> = _upComingFilm

    private val _backInDaysFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val backInDaysFilm: MutableState<Flow<PagingData<Film>>> = _backInDaysFilm

    var selectedFilmType: MutableState<FilmType> = mutableStateOf(FilmType.MOVIE)

    val selectedGenre: MutableState<Genre> = mutableStateOf(Genre(null, "All"))
    private var _filmGenre = mutableStateListOf(Genre(null, "All"))
    val filmGenre: SnapshotStateList<Genre> = _filmGenre

    init {
        hitNetworkCall()
    }

    fun hitNetworkCall(
        filmType: FilmType = selectedFilmType.value,
        genreId: Int? = selectedGenre.value.id,
    ) {
        genreFilmChip()
        trendingFilmNetwork(filmType, genreId)
        popularFilmNetwork(filmType, genreId)
        topRatedFilmNetwork(filmType, genreId)
        nowPlayingFilmNetwork(filmType, genreId)
        upComingFilmNetwork(filmType, genreId)
        backInDayFilmNetwork(filmType, genreId)
    }

    fun genreFilmChip() {
        viewModelScope.launch {
            val defaultGenre = Genre(null, "All")
            when (val resultChip = genreFilmRepository.genreFilm(selectedFilmType.value)) {
                is ResourceState.Success -> {
                    _filmGenre.clear()
                    _filmGenre.add(defaultGenre)
                    resultChip.data?.genres?.forEach {
                        _filmGenre.add(it)
                    }
                }
                is ResourceState.Error -> {
                    Timber.e("Error loading Genres")
                }
                else -> {}
            }
        }
    }

    fun trendingFilmNetwork(filmType: FilmType, genreId: Int?) {
        viewModelScope.launch {
            _trendingFilm.value = filterItem(
                genreId,
                homeRepository.trendingFilm(filmType)
            ).cachedIn(viewModelScope)
        }
    }

    fun popularFilmNetwork(filmType: FilmType, genreId: Int?) {
        viewModelScope.launch {
            _popularFilm.value = filterItem(
                genreId,
                homeRepository.popularFilm(filmType)
            ).cachedIn(viewModelScope)
        }
    }

    fun topRatedFilmNetwork(filmType: FilmType, genreId: Int?) {
        viewModelScope.launch {
            _topRatedFilm.value = filterItem(
                genreId,
                homeRepository.topRateFilm(filmType)
            ).cachedIn(viewModelScope)
        }
    }

    fun nowPlayingFilmNetwork(filmType: FilmType, genreId: Int?) {
        viewModelScope.launch {
            _nowPlayingFilm.value = filterItem(
                genreId,
                homeRepository.nowPlayingFilm(filmType)
            ).cachedIn(viewModelScope)
        }
    }

    fun upComingFilmNetwork(filmType: FilmType, genreId: Int?) {
        viewModelScope.launch {
            _upComingFilm.value =
                filterItem(
                    genreId,
                    homeRepository.upComingFilm(filmType)
                ).cachedIn(viewModelScope)
        }
    }

    fun backInDayFilmNetwork(filmType: FilmType, genreId: Int?) {
        viewModelScope.launch {
            _backInDaysFilm.value =
                filterItem(
                    genreId,
                    homeRepository.backInDaysFilm(filmType)
                ).cachedIn(viewModelScope)
        }
    }

    fun filterItem(genreId: Int?, data: Flow<PagingData<Film>>): Flow<PagingData<Film>> {
        return if (genreId != null) {
            data.map { result ->
                result.filter { film ->
                    film.genreIds!!.contains(genreId)
                }
            }
        } else {
            data
        }
    }
}