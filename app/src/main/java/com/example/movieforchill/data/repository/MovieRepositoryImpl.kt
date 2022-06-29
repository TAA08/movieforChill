package com.example.movieforchill.data.repository

import android.app.Application
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.domain.models.movie.PostMovie
import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.data.net.MoviesApiService
import com.example.movieforchill.data.net.RetrofitInstance
import com.example.movieforchill.data.room.MovieDao
import com.example.movieforchill.domain.models.actors.Cast
import com.example.movieforchill.domain.models.actors.Credits
import com.example.movieforchill.domain.repository.MovieRepository
import com.example.movieforchill.presentation.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MovieRepositoryImpl(
    application: Application,
    private val api: MoviesApiService,
    private val db: MovieDao
) : MovieRepository {


    private var context = application


    override suspend fun loadMovie(page: Int): List<Result>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMoviesList(page = page)
                if (response.isSuccessful && isFirstDownloaded) {

                    isFirstDownloaded = false

                    val result = response.body()?.results
                    if (!result.isNullOrEmpty()) {
                        db.insertAll(result)
                    }
                    result
                } else {
                    db.getAll()

                }
            } catch (e: Exception) {
                db.getAll()
            }

        }
    }

    override suspend fun getDetail(movieId: Int, sessionId: String?): Result {
        return withContext(Dispatchers.IO) {
            try {
                val result = db.getMovieById(movieId)
                if (sessionId != null) {
                    val response = api.getMovieStates(
                        movieId,
                        session_id = sessionId
                    )
                    if (response.isSuccessful) {
                        val favouriteState = response.body()?.favorite as Boolean
                        result.favouriteState = favouriteState
                    }
                }
                db.updateState(result)
                result
            } catch (e: Exception) {
                db.getMovieById(movieId)
            }
        }
    }

    override suspend fun changeFavouriteState(movieId: Int): Result {
        return withContext(Dispatchers.IO) {
            val movie = db.getMovieById(movieId)
            val newMovie = movie.copy(favouriteState = !movie.favouriteState)
            db.updateState(newMovie)
            newMovie

        }
    }

    override suspend fun postMovieState(movieId: Int, sessionId: String, movie: Result) {
        try {
            val postMovie = PostMovie(
                media_id = movieId,
                favorite = movie.favouriteState
            )
            api.addFavorite(
                session_id = sessionId, postMovie = postMovie
            )
        } catch (e: Exception) {
            Toast.makeText(context, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun getFavouriteMovie(sessionId: String): List<Result>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.getPostApi().getFavorites(session_id = sessionId)
                if (response.isSuccessful) {
                    MainActivity.isFirstDownloaded = false
                    val result = response.body()?.results
                    if (!result.isNullOrEmpty()) {
                        db.insertAll(result)
                    }
                    result
                } else {
                    db.getFavouriteMovies()

                }
            } catch (e: Exception) {
                db.getFavouriteMovies()
            }
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Response<Credits> {
        return withContext(Dispatchers.IO){
            api.getMovieCredits(movieId)
        }


    }


    companion object {
        private var isFirstDownloaded = true

    }
}