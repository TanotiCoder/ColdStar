package com.example.coldstar.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.data.remote.responce.Review
import com.example.coldstar.paging.ReviewSource
import com.example.coldstar.utlis.FilmType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepository @Inject constructor(private val apiService: ApiService) {
    fun getReviewData(mediaId: Int, filmType: FilmType): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ReviewSource(apiService, filmType, mediaId) }
        ).flow
    }
}