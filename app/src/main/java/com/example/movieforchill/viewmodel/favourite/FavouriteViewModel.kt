package com.example.movieforchill.viewmodel.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.model.movie.Result
import com.example.movieforchill.model.room.repository.MovieRepository
import com.example.movieforchill.viewmodel.main.MovieViewModel
import kotlinx.coroutines.launch

class FavouriteViewModel(
    val repository: MovieRepository
) : ViewModel() {


    private val _loadingState = MutableLiveData<MovieViewModel.State>()
    val loadingState: LiveData<MovieViewModel.State>
        get() = _loadingState

    private val _movies = MutableLiveData<List<Result>?>()
    val movies: MutableLiveData<List<Result>?>
        get() = _movies

    fun getPosts(session: String) {
        viewModelScope.launch {
            _loadingState.value = MovieViewModel.State.ShowLoading

            _movies.value = repository.getFavouriteMovie(session)

            _loadingState.value = MovieViewModel.State.HideLoading
            _loadingState.value = MovieViewModel.State.Finish
        }

    }

}