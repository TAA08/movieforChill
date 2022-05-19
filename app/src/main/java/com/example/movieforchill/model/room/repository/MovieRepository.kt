package com.example.movieforchill.model.room.repository

import android.app.Application
import android.widget.Toast
import com.example.movieforchill.model.PostMovie
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(application: Application) {

    private val workWithApi = RetrofitInstance.getPostApi()
    private val db = MovieDatabase.getDatabase(application).movieDao()

    private var context = application


    suspend fun loadMovie(page: Int): List<Result>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = workWithApi.getMoviesList(page = page)
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

    suspend fun getDetail(movieId: Int, sessionId: String?): Result {
        return withContext(Dispatchers.IO) {
            try {
                val result = db.getMovieById(movieId)
                if (sessionId != null) {
                    val response = workWithApi.getMovieStates(
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

    suspend fun changeFavouriteState(movieId: Int): Result {
        return withContext(Dispatchers.IO) {
            val movie = db.getMovieById(movieId)
            val newMovie = movie.copy(favouriteState = !movie.favouriteState)
            db.updateState(newMovie)
            newMovie

        }
    }

    suspend fun postMovieState(movieId: Int, sessionId: String , movie: Result) {
        try {
            val postMovie = PostMovie(
                media_id = movieId,
                favorite = movie.favouriteState
            )
            workWithApi.addFavorite(
                session_id = sessionId, postMovie = postMovie
            )
        } catch (e: Exception) {
            Toast.makeText(context, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun  getFavouriteMovie(sessionId: String) : List<Result>?{
        return withContext(Dispatchers.IO){
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




    companion object {
        private var isFirstDownloaded = true

    }
}