package com.example.movieforchill.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieforchill.model.MoviesModel
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.view.adapter.main_adapter.MainMoviesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main


    private val _loadingState = MutableLiveData<State>()
    val loadingState: LiveData<State>
        get() = _loadingState

    private val _movies = MutableLiveData<List<Result>>()
    val movies: LiveData<List<Result>>
        get() =_movies

    private val _openDetail = MutableLiveData<Result>()
    val openDetail: LiveData<Result>
        get() =_openDetail

    init {
        getPosts()
    }

    private fun getPosts() {
        launch {
            _loadingState.value = State.ShowLoading
            val response = RetrofitInstance.getPostApi().getMoviesList()
            if (response.isSuccessful){
                _movies.value = response.body()?.results
            }
            _loadingState.value = State.HideLoading
            _loadingState.value = State.Finish
        }
    }

    val recyclerViewItemClickListener = object : MainMoviesAdapter.OnMovieClickListener {

        override fun onMovieClick(result: Result) {
            _openDetail.value = result
        }
    }


    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        object Finish : State()

    }
}