package com.example.coldstar.di

import android.content.Context
import androidx.room.Room
import com.example.coldstar.data.local.MovieDao
import com.example.coldstar.data.local.MovieDatabase
import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.repository.HomeRepository
import com.example.coldstar.repository.MyListMovieRepository
import com.example.coldstar.repository.WatchOttRepository
import com.example.coldstar.utlis.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "watch_list_table")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()

    @Singleton
    @Provides
    fun provideMovieRepository(apiService: ApiService): HomeRepository =
        HomeRepository(apiService)

    @Singleton
    @Provides
    fun provideWatchOttRepository(apiService: ApiService): WatchOttRepository =
        WatchOttRepository(apiService)

    @Singleton
    @Provides
    fun provideMyListRepository(movieDao: MovieDao): MyListMovieRepository =
        MyListMovieRepository(movieDao = movieDao)
}