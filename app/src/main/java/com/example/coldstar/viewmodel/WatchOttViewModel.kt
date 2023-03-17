package com.example.coldstar.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coldstar.data.remote.responce.WatchProvider
import com.example.coldstar.repository.WatchOttRepository
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
//
//@HiltViewModel
//class WatchOttViewModel @Inject constructor(private val watchOttRepository: WatchOttRepository) :
//    ViewModel() {
//    private val _watchOttState = mutableStateOf<WatchProvider?>(null)
//    val watchOttState: MutableState<WatchProvider?> = _watchOttState
//
//    fun watchOttFun(filmType: FilmType, mediaId: Int) {
//        viewModelScope.launch {
//            watchOttRepository.getWatchOtt(filmType = filmType, mediaId = mediaId).also {
//                if (it is ResourceState.Success){
//                    _watchOttState.value = it.data!!.results
//                }
//            }
//        }
//    }
//}