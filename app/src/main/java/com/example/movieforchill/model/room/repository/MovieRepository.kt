package com.example.movieforchill.model.room.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.movieforchill.model.LoginApprove
import com.example.movieforchill.model.PostMovie
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.Token
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MovieRepository(application: Application) {

    private val workWithApi = RetrofitInstance.getPostApi()
    private val db = MovieDatabase.getDatabase(application).movieDao()
    private var prefSettings: SharedPreferences = application.getSharedPreferences(
        APP_SETTINGS,
        Context.MODE_PRIVATE
    ) as SharedPreferences
    private var editor: SharedPreferences.Editor = prefSettings.edit()
    private var context = application


    suspend fun loadMovie(page: Int): List<Result>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = workWithApi.getMoviesList()
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

    suspend fun changeFavouriteState(movieId: Int, sessionId: String): Result {
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

    suspend fun login(username : String, password : String) : String {
        var session = ""
        try {
            val responseGet = workWithApi.getToken()
            if (responseGet.isSuccessful) {
                val loginApprove = LoginApprove(
                    username = username,
                    password = password,
                    request_token = responseGet.body()?.request_token as String
                )
                val responseApprove = workWithApi.approveToken(
                    loginApprove = loginApprove
                )
                if (responseApprove.isSuccessful) {
                    val response =
                        workWithApi.createSession(token = responseApprove.body() as Token)
                    if (response.isSuccessful){
                        session = response.body()?.session_id as String
                    }
                }
            }
            else{
                Toast.makeText(context, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
            }
        } catch (e : Exception){
            Toast.makeText(context, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
        }

        return session
    }


    companion object {

        const val APP_SETTINGS = "Settings"
        private var isFirstDownloaded = true
        const val FRAGMENTS_KEY = "SESSION_FRAGMENT"
        const val LOGIN_KEY = "SESSION_LOGIN"
        const val CURRENT_USER_ID = "CURRENT_USER"
    }
}