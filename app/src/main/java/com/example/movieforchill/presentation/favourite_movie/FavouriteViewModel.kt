package com.example.movieforchill.presentation.favourite_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.usecases.GetFavouriteMovieUseCase
import com.example.movieforchill.presentation.movie.MovieViewModel
import kotlinx.coroutines.launch

class FavouriteViewModel(
    val favouriteMovieUseCase: GetFavouriteMovieUseCase
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

            _movies.value = favouriteMovieUseCase.invoke(session)

            _loadingState.value = MovieViewModel.State.HideLoading
            _loadingState.value = MovieViewModel.State.Finish
        }

    }

}