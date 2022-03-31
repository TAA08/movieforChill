package com.example.movieforchill.data.retrofit.api

import com.example.movieforchill.screens.main.MoviesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService { // интерфейс для создания гет запроса
    @GET("movie/popular")
    fun getMoviesList(
        @Query("api_key") apiKey:String = "2672c5e5006041431f2be6a17b6f7ceb",
        @Query("language") language:String = "ru",
        @Query("page") page:Int = 1
    ): Call<MoviesModel>


}