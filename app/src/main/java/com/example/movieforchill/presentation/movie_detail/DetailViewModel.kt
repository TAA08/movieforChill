package com.example.movieforchill.presentation.movie_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.domain.models.actors.Cast
import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.usecases.ChangeMovieStateUseCase
import com.example.movieforchill.domain.usecases.GetCreditsUseCase
import com.example.movieforchill.domain.usecases.GetMovieDetailUseCase
import com.example.movieforchill.domain.usecases.PostMovieStateUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    application: Application,
    val detailUseCase: GetMovieDetailUseCase,
    val changeMovieStateUseCase: ChangeMovieStateUseCase,
    val postMovieStateUseCase: PostMovieStateUseCase,
    val getCreditsUseCase: GetCreditsUseCase

) : AndroidViewModel(application) {

    private val context = application


    private val _liveDataDetail = MutableLiveData<Result>()
    val liveDataDetail: LiveData<Result>
        get() = _liveDataDetail

    private val _loadingState = MutableLiveData<StateDetail>()
    val loadingState: LiveData<StateDetail>
        get() = _loadingState

    private val _actors = MutableLiveData<List<Cast>>()
    val actors: LiveData<List<Cast>>
        get() = _actors


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
            val movieState = detailUseCase.invoke(movieId, sessionId)
//            if (movieState.favouriteState){
//                AlertDialog
//                    .Builder(context)
//                    .setMessage("Удалить фильм ${movieState.title} из избранных?")
//                    .setPositiveButton("Да") { _, _ ->
//                        viewModelScope.launch{
//                            val movie = changeMovieStateUseCase.invoke(movieId)
//                            _liveDataDetail.value = movie
//                            postMovieStateUseCase(movieId, sessionId, movie)
//                        }
//                    }
//                    .setNegativeButton("Нет") { _, _ -> }
//                    .create()
//                    .show()
//
//            }else{
            val movie = changeMovieStateUseCase.invoke(movieId)
            _liveDataDetail.value = movie
            postMovieStateUseCase(movieId, sessionId, movie)


        }
    }

    fun getActors(movieId: Int){
        viewModelScope.launch {
            _actors.value = getCreditsUseCase.invoke(movieId).body()?.cast
        }
    }

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        object Finish : StateDetail()
    }
}