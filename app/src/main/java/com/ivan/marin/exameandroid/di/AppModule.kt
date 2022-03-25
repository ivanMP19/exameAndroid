package com.ivan.marin.exameandroid.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ivan.marin.exameandroid.data.local.AppDatabase
import com.ivan.marin.exameandroid.data.local.MovieDao
import com.ivan.marin.exameandroid.data.remote.MovieRemoteDataSource
import com.ivan.marin.exameandroid.data.remote.MovieService
import com.ivan.marin.exameandroid.data.repository.MovieRepository
import com.ivan.marin.exameandroid.utils.Constantes.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(movieService: MovieService) = MovieRemoteDataSource(movieService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase) = db.moviesDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: MovieRemoteDataSource,
                          localDataSource: MovieDao
    ) = MovieRepository(remoteDataSource, localDataSource)
}