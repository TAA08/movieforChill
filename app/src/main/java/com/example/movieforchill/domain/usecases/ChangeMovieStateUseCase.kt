package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.repository.MovieRepository

class ChangeMovieStateUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId : Int) : Result{
        return repository.changeFavouriteState(movieId = movieId)
    }
}