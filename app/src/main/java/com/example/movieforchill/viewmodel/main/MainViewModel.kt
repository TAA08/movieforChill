package com.example.movieforchill.viewmodel.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.model.room.dao.MovieDao
import com.example.movieforchill.model.room.repository.MovieDatabase
import com.example.movieforchill.view.adapter.main_adapter.MainMoviesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val context: Context
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao


    private val _loadingState = MutableLiveData<State>()
    val loadingState: LiveData<State>
        get() = _loadingState

    private val _movies = MutableLiveData<List<Result>?>()
    val movies: MutableLiveData<List<Result>?>
        get() = _movies

    private val _openDetail = MutableLiveData<Result>()
    val openDetail: LiveData<Result>
        get() = _openDetail

    init {
        getPosts()
        movieDao = MovieDatabase.getDatabase(context).movieDao()
    }

    private fun getPosts() {
        launch {
            _loadingState.value = State.ShowLoading
            val list = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitInstance.getPostApi().getMoviesList()
                    if (response.isSuccessful) {
                        val result = response.body()?.results
                        if (!result.isNullOrEmpty()) {
                            movieDao.insertAll(result)
                        }
                        result
                    } else {
                        movieDao.getAll()

                    }
                } catch (e: Exception) {
                    movieDao.getAll()
                }
            }
            _movies.value = list

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



