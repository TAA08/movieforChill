package com.example.movieforchill.viewmodel.main

import android.app.Application
import androidx.lifecycle.*
import com.example.movieforchill.model.movie.Result
import com.example.movieforchill.model.room.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    val repository: MovieRepository
) : ViewModel() {

    private val _loadingState = MutableLiveData<State>()
    val loadingState: LiveData<State>
        get() = _loadingState

    private val _movies = MutableLiveData<List<Result>?>()
    val movies: MutableLiveData<List<Result>?>
        get() = _movies


    fun getPosts(page: Int) {
        viewModelScope.launch {
            _loadingState.value = State.ShowLoading

            _movies.value = repository.loadMovie(page)

            _loadingState.value = State.HideLoading
            _loadingState.value = State.Finish
        }

    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        object Finish : State()

    }
}

//    val recyclerViewItemClickListener = object : MainMoviesAdapter.OnMovieClickListener {
//
//        override fun onMovieClick(result: Result) {
//            _openDetail.value = result
//        }
//    }



