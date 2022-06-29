package com.example.movieforchill.domain.repository

import com.example.movieforchill.domain.models.actors.Credits
import com.example.movieforchill.domain.models.movie.Result
import retrofit2.Response

interface MovieRepository {

    suspend fun loadMovie(page: Int): List<Result>?

    suspend fun getDetail(movieId: Int, sessionId: String?): Result

    suspend fun changeFavouriteState(movieId: Int): Result

    suspend fun postMovieState(movieId: Int, sessionId: String , movie: Result)

    suspend fun  getFavouriteMovie(sessionId: String) : List<Result>?

    suspend fun getMovieCredits(movieId: Int) : Response<Credits>
}