package com.example.movieforchill.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.usecases.GetMoviesListUseCase
import kotlinx.coroutines.launch

class MovieViewModel(
    val useCase: GetMoviesListUseCase
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

            _movies.value = useCase.invoke(page = page)

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



