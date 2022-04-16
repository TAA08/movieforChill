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

    private var oldList = mutableListOf<Result>()
    private var newList = mutableListOf<Result>()


    private val _loadingState = MutableLiveData<State>()
    val loadingState: LiveData<State>
        get() = _loadingState

    private val _movies = MutableLiveData<List<Result>>()
    val movies: LiveData<List<Result>>
        get() =_movies

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

            _liveData.value = State.HideLoading
        }
    }

    val recyclerViewItemClickListener = object : MainMoviesAdapter.OnMovieClickListener {

        override fun onMovieClick(result: Result) {
            _openDetail.value = result
        }
    }

    val scrollList = object  : MainMoviesAdapter.OnReachEndListener{
        override fun onReachEnd() {
            TODO("Not yet implemented")
        }

    }


    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Results(val list: MoviesModel) : State()
    }
}