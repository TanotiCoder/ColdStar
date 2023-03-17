package com.example.coldstar.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.data.remote.responce.Review
import com.example.coldstar.utlis.FilmType
import retrofit2.HttpException
import java.io.IOException

class ReviewSource(
    private val apiService: ApiService,
    private val filmType: FilmType,
    private val mediaId: Int
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val nextPage = params.key ?: 1
            val reviewData = apiService.getMovieReviews(
                filmId = mediaId,
                filmPath = if (filmType == FilmType.MOVIE) "movie" else "tv",
                page = nextPage
            )
            LoadResult.Page(
                nextKey = if (reviewData.results.isEmpty()) null else reviewData.page + 1,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                data = reviewData.results
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

}