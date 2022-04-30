package com.example.movieforchill.viewmodel.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieforchill.model.models.PostMovie
import com.example.movieforchill.model.models.Result
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.model.room.dao.MovieDao
import com.example.movieforchill.model.room.database.MovieDatabase
import com.example.movieforchill.view.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    private val context: Context
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao

    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState

    private val _addFavoriteState = MutableLiveData<LoadingState>()
    val addFavoriteState: LiveData<LoadingState>
        get() = _addFavoriteState

    init {
        movieDao = MovieDatabase.getDatabase(context).movieDao()
    }


    fun getMovieDetails(movieId: Int) {
        launch {
            _loadingState.value = StateDetail.ShowLoading
            val list = withContext(Dispatchers.IO) {
                var result = movieDao.getMovieById(movieId)
                result
            }

            _liveDataDetail.value = list


            _loadingState.value = StateDetail.HideLoading
            _loadingState.value = StateDetail.Finish
        }
    }

    fun addFavorite(movieId: Int, sessionId: String) {
        launch {
            val postMovie = PostMovie(media_id = movieId, favorite = true)
            val response = RetrofitInstance.getPostApi().addFavorite(
                session_id = sessionId,
                postMovie = postMovie
            )
            val movie = movieDao.getMovieById(movieId)
            val newMovie = movie.copy()

            var loadingState = if (response.isSuccessful) {
                LoadingState.SUCCESS
            } else {
                LoadingState.FINISHED
            }
            _addFavoriteState.value = loadingState
            _addFavoriteState.value = LoadingState.IS_LOADING

        }
    }

    fun deleteFavorite(movieId: Int, sessionId: String) {
        launch {
            val postMovie = PostMovie(media_id = movieId, favorite = false)
            val response = RetrofitInstance.getPostApi().addFavorite(
                session_id = sessionId,
                postMovie = postMovie
            )
            var loadingState = if (response.isSuccessful) {
                LoadingState.SUCCESS
            } else {
                LoadingState.FINISHED
            }
            _addFavoriteState.value = loadingState
            _addFavoriteState.value = LoadingState.IS_LOADING

        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }
}