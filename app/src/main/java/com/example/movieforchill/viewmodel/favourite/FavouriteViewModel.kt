package com.example.movieforchill.viewmodel.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.room.repository.MovieRepository
import com.example.movieforchill.viewmodel.main.MovieViewModel
import kotlinx.coroutines.launch

class FavouriteViewModel(
    application: Application
) : AndroidViewModel(application) {

    val repository = MovieRepository(application)


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