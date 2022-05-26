package com.example.movieforchill.presentation.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.usecases.ChangeMovieStateUseCase
import com.example.movieforchill.domain.usecases.GetMovieDetailUseCase
import com.example.movieforchill.domain.usecases.PostMovieStateUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    val detailUseCase: GetMovieDetailUseCase,
    val changeMovieStateUseCase: ChangeMovieStateUseCase,
    val postMovieStateUseCase: PostMovieStateUseCase

) :ViewModel() {


    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState


    fun getMovieDetails(movieId: Int, sessionId: String?) {
        viewModelScope.launch {
            _loadingState.value = StateDetail.ShowLoading


            _liveDataDetail.value = detailUseCase.invoke(movieId, sessionId)


            _loadingState.value = StateDetail.HideLoading
            _loadingState.value = StateDetail.Finish
        }
    }

    fun addOrDeleteFavorite(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val movie = changeMovieStateUseCase.invoke(movieId)
            _liveDataDetail.value = movie
            postMovieStateUseCase(movieId, sessionId, movie)
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }
}