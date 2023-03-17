package com.example.coldstar.repository

import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.data.remote.responce.WatchProviderResponse
import com.example.coldstar.utlis.FilmType
import com.example.coldstar.utlis.ResourceState
import retrofit2.HttpException
import javax.inject.Inject

class WatchOttRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getWatchOtt(
        mediaId: Int,
        filmType: FilmType
    ): ResourceState<WatchProviderResponse> {
        val data = try {
            apiService.getWatchProviders(
                filmPath = if (filmType == FilmType.MOVIE) "movie" else "tv",
                filmId = mediaId
            )
        } catch (e: Exception) {
            return ResourceState.Error(e.localizedMessage)
        } catch (e: HttpException) {
            return ResourceState.Error(e.localizedMessage)
        }
        return ResourceState.Success(data)
    }
}