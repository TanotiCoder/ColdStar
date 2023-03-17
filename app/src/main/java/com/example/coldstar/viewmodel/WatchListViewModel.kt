package com.example.coldstar.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coldstar.data.local.MyListMovie
import com.example.coldstar.repository.MyListMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val myListMovieRepository: MyListMovieRepository) :
    ViewModel() {
    private var _exist = mutableStateOf(0)
    val exist: State<Int> = _exist

    private val _myMovieData = mutableStateOf<Flow<List<MyListMovie>>>(emptyFlow())
    val myMovieData: State<Flow<List<MyListMovie>>> = _myMovieData

    init {
        allMovieData()
    }

    private fun allMovieData() {
       _myMovieData.value = myListMovieRepository.getAllData()
    }

    fun exist(mediaId: Int) {
        viewModelScope.launch {
            _exist.value = myListMovieRepository.exist(mediaId = mediaId)
        }
    }

    fun addToWatchList(movie: MyListMovie) {
        viewModelScope.launch {
            myListMovieRepository.insertMovieInList(movie)
        }.invokeOnCompletion {
            exist(movie.mediaId)
        }
    }

    fun removeFromWatchList(mediaId: Int) {
        viewModelScope.launch {
            myListMovieRepository.removeFromList(mediaId = mediaId)
        }.invokeOnCompletion {
            exist(mediaId = mediaId)
        }
    }

}