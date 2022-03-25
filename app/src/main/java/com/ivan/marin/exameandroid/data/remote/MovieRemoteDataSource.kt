package com.ivan.marin.exameandroid.data.remote

class MovieRemoteDataSource constructor(
    private val movieService: MovieService
): BaseDataSource() {

    suspend fun getMovies(key:String,lenguage:String,page:Int) = getResult { movieService.getMovies(key,lenguage,page) }
}