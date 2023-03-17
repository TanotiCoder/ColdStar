package com.example.coldstar.data.remote

import com.example.coldstar.data.remote.responce.*
import com.example.coldstar.utlis.Utils
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovie(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") lang: String = "en"
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") lang: String = "en"
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("discover/movie?")
    suspend fun getBackInTheDaysMovies(
        @Query("page") page: Int = 0,
        @Query("primary_release_date.gte") gteReleaseDate: String = "1940-01-01",
        @Query("primary_release_date.lte") lteReleaseDate: String = "1981-01-01",
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en",
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): MovieResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): GenreResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") filmId: Int,
        @Query("api_key") apiKey: String = Utils.apiKey
    ): CastResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "aa36b8bcd1f429979039d88d274a960b",
        @Query("language") language: String = "en"
    ): MovieResponse

    /** **Tv Shows** */

    @GET("trending/tv/day")
    suspend fun getTrendingTvSeries(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") lang: String = "en"
    ): MovieResponse


    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") lang: String = "en"
    ): MovieResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvSeries(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("tv/on_the_air")
    suspend fun getNowPlayingTvSeries(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingTvSeries(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): MovieResponse

    @GET("discover/tv?")
    suspend fun getBackInTheDaysTvSeries(
        @Query("page") page: Int = 0,
        @Query("primary_release_date.gte") gteReleaseDate: String = "1940-01-01",
        @Query("primary_release_date.lte") lteReleaseDate: String = "1981-01-01",
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en",
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): MovieResponse

    @GET("genre/tv/list")
    suspend fun getTvShowGenres(
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en-US"
    ): GenreResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCast(
        @Path("tv_id") filmId: Int,
        @Query("api_key") apiKey: String = Utils.apiKey
    ): CastResponse

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    /** Search Engine */

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") searchParams: String,
        @Query("page") page: Int = 0,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en"
    ): MultiSearchResponse


    /** Reviews*/
    @GET("{film_path}/{film_id}/reviews?")
    suspend fun getMovieReviews(
        @Path("film_id") filmId: Int,
        @Path("film_path") filmPath: String,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = Utils.apiKey,
        @Query("language") language: String = "en-US"
    ): ReviewsResponse

    /** Watch providers (US only)*/

    @GET("{film_path}/{film_id}/watch/providers?")
    suspend fun getWatchProviders(
        @Path("film_path") filmPath: String,
        @Path("film_id") filmId: Int,
        @Query("api_key") apiKey: String = Utils.apiKey,
    ): WatchProviderResponse
}