package com.example.coldstar.repository

import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.data.remote.responce.GenreResponse
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.ResourceState
import retrofit2.HttpException
import javax.inject.Inject

class GenreFilmRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun genreFilm(filmType: FilmType): ResourceState<GenreResponse> {
        val response = try {
            if (filmType == FilmType.MOVIE)
                apiService.getMovieGenres()
            else apiService.getTvShowGenres()
        } catch (e: Exception) {
            return ResourceState.Error(e.localizedMessage)
        } catch (e: HttpException) {
            return ResourceState.Error(e.localizedMessage)
        }
        return ResourceState.Success(response)
    }
}