package com.example.movieforchill.data.retrofit.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun getPostApi(): MoviesApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MoviesApiService::class.java)
    }

    fun getMovieDetail(): MoviesApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MoviesApiService::class.java)
    }
}

