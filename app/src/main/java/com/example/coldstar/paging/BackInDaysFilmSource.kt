package com.example.coldstar.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.model.Film
import com.example.coldstar.utlis.FilmType
import retrofit2.HttpException
import java.io.IOException

class BackInDaysFilmSource(private val apiService: ApiService, private val filmType: FilmType) :
    PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val nextPage = params.key ?: 1
            val treadingFilmData = if (filmType == FilmType.MOVIE)
                apiService.getBackInTheDaysMovies(page = nextPage)
            else
                apiService.getBackInTheDaysTvSeries(page = nextPage)
            LoadResult.Page(
                nextKey = if (treadingFilmData.results.isEmpty()) null else nextPage + 1,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                data = treadingFilmData.results
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}

