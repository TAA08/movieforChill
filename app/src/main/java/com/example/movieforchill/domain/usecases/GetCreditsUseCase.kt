package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.models.actors.Credits
import com.example.movieforchill.domain.repository.MovieRepository
import retrofit2.Response

class GetCreditsUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int) : Response<Credits> {
        return repository.getMovieCredits(movieId)
    }
}