package com.ivan.marin.exameandroid.data.remote

import com.ivan.marin.exameandroid.data.entities.MovieModelList
import com.ivan.marin.exameandroid.utils.Constantes.API_KEY_REQUEST_PARAM
import com.ivan.marin.exameandroid.utils.Constantes.LANGUAGE_REQUEST_PARAM
import com.ivan.marin.exameandroid.utils.Constantes.PAGE_REQUEST_PARAM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    //search movies
    @GET(".")
    suspend fun getMovies(@Query(API_KEY_REQUEST_PARAM) Key:String,@Query(LANGUAGE_REQUEST_PARAM) language:String,@Query(PAGE_REQUEST_PARAM) page:Int):Response<MovieModelList>

}
