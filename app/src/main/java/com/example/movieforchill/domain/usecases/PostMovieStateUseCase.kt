package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.repository.MovieRepository

class PostMovieStateUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int, sessionId: String , movie: Result) {
        return repository.postMovieState(movieId, sessionId, movie)
    }
}