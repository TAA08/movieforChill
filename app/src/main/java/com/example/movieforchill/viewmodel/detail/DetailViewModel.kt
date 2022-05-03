package com.example.movieforchill.viewmodel.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieforchill.model.PostMovie
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.model.room.dao.MovieDao
import com.example.movieforchill.model.room.repository.MovieDatabase
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

    fun addOrDeleteFavorite(movieId: Int, sessionId: String) {
        launch {
            val favouriteState = withContext(Dispatchers.IO) {
                val movie = movieDao.getMovieById(movieId)
                val newMovie = movie.copy(favouriteState = !movie.favouriteState)
                movieDao.updateState(newMovie)
                newMovie
            }
            _liveDataDetail.value = favouriteState
            val postMovie = PostMovie(media_id = movieId, favorite = favouriteState.favouriteState)
            RetrofitInstance.getPostApi().addFavorite(session_id = sessionId, postMovie = postMovie)
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }
}