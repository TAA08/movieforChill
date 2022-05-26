package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.repository.MovieRepository

class GetMoviesListUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(page : Int) : List<Result>?{
        return repository.loadMovie(page)
    }

}