package com.example.coldstar.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.data.remote.responce.CastResponse
import com.example.coldstar.model.Film
import com.example.coldstar.paging.*
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.ResourceState
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {
    fun trendingFilm(filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                TrendingFilmSource(
                    apiService = apiService,
                    filmType = filmType
                )
            }
        ).flow
    }

    fun popularFilm(filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                PopularFilmSource(
                    apiService = apiService,
                    filmType = filmType
                )
            }
        ).flow
    }

    fun topRateFilm(filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                TopRatedFilmSource(
                    apiService = apiService,
                    filmType = filmType
                )
            }
        ).flow
    }

    fun nowPlayingFilm(filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                NowPlayingFilmSource(
                    apiService = apiService,
                    filmType = filmType
                )
            }
        ).flow
    }

    fun upComingFilm(filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                UpComingFilmSource(
                    apiService = apiService,
                    filmType = filmType
                )
            }
        ).flow
    }

    fun backInDaysFilm(filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                BackInDaysFilmSource(
                    apiService = apiService,
                    filmType = filmType
                )
            }
        ).flow
    }

    fun getSimilarFilm(mediaId: Int, filmType: FilmType): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                SimilarMovieSource(
                    apiService,
                    filmId = mediaId,
                    filmType = filmType
                )
            }
        ).flow
    }

    suspend fun getFilmCast(filmType: FilmType, mediaId: Int): ResourceState<CastResponse> {
        val resultResponse = try {
            if (filmType == FilmType.MOVIE)
                apiService.getMovieCast(mediaId)
            else apiService.getTvShowCast(mediaId)
        } catch (e: Exception) {
            return ResourceState.Error(e.localizedMessage!!)
        } catch (e: HttpException) {
            return ResourceState.Error(e.localizedMessage!!)
        }
        return ResourceState.Success(resultResponse)
    }
}

