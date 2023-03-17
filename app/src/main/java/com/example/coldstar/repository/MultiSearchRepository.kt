package com.example.coldstar.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.model.Search
import com.example.coldstar.paging.MultiSearchSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MultiSearchRepository @Inject constructor(private val apiService: ApiService) {
    fun searchResult(searchPars: String): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { MultiSearchSource(apiService, searchPars) }
        ).flow
    }
}