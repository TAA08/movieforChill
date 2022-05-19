package com.example.movieforchill.viewmodel.favourite

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.model.room.dao.MovieDao
import com.example.movieforchill.model.room.repository.MovieDatabase
import com.example.movieforchill.model.room.repository.MovieRepository
import com.example.movieforchill.view.MainActivity.Companion.isFirstDownloaded
import com.example.movieforchill.viewmodel.main.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

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