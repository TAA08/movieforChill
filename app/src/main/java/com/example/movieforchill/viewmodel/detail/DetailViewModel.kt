package com.example.movieforchill.viewmodel.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.room.repository.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    application: Application
) : AndroidViewModel(application) {

    var repository = MovieRepository(application)


    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState


    fun getMovieDetails(movieId: Int, sessionId: String?) {
        viewModelScope.launch {
            _loadingState.value = StateDetail.ShowLoading


            _liveDataDetail.value = repository.getDetail(movieId, sessionId)


            _loadingState.value = StateDetail.HideLoading
            _loadingState.value = StateDetail.Finish
        }
    }

    fun addOrDeleteFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val movie = repository.changeFavouriteState(movieId, sessionId)
            _liveDataDetail.value = movie
            repository.postMovieState(movieId, sessionId, movie)
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }
}