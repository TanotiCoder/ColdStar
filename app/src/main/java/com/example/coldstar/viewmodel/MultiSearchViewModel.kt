package com.example.coldstar.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.coldstar.model.Search
import com.example.coldstar.repository.MultiSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiSearchViewModel @Inject constructor(private val multiSearchRepository: MultiSearchRepository) :
    ViewModel() {
    val searchParam = mutableStateOf("")
    val previousSearch = mutableStateOf("")

    private var _multiSearch = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val multiSearchState: State<Flow<PagingData<Search>>> = _multiSearch

    init {
        searchParam.value = ""
    }

    fun searchFilm() {
        viewModelScope.launch {
            if (searchParam.value.isNotEmpty()) {
                _multiSearch.value =
                    multiSearchRepository.searchResult(searchParam.value).cachedIn(viewModelScope)
                        .map { result ->
                            result.filter {
                                ((it.title != null || it.originalName != null || it.originalTitle != null) &&
                                        (it.mediaType == "tv" || it.mediaType == "movie"))
                            }
                        }
            }
        }
    }
}