package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, sessionId: String?) : Result{
        return repository.getDetail(movieId = movieId, sessionId = sessionId)
    }
}