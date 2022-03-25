package com.ivan.marin.exameandroid.data.repository

import com.ivan.marin.exameandroid.data.local.MovieDao
import com.ivan.marin.exameandroid.data.remote.MovieRemoteDataSource
import com.ivan.marin.exameandroid.utils.Constantes.API_KEEY
import com.ivan.marin.exameandroid.utils.Constantes.LANGUAGE
import com.ivan.marin.exameandroid.utils.performGetOperation
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieDao
){
    fun getMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getMovies(API_KEEY,LANGUAGE,1) },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )

    fun getMovie(id: Int) = localDataSource.getMovies(id)

}