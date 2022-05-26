package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.repository.MovieRepository

class GetFavouriteMovieUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(sessionId: String) : List<Result>? {
        return repository.getFavouriteMovie(sessionId = sessionId)
    }
}