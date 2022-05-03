package com.example.movieforchill.viewmodel.favourite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.model.room.dao.MovieDao
import com.example.movieforchill.model.room.repository.MovieDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import com.example.movieforchill.model.Result
import com.example.movieforchill.viewmodel.main.MovieViewModel

class FavouriteViewModel(
    private val context: Context
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val movieDao: MovieDao

    private val _loadingState = MutableLiveData<MovieViewModel.State>()
    val loadingState: LiveData<MovieViewModel.State>
        get() = _loadingState

    private val _movies = MutableLiveData<List<Result>?>()
    val movies: MutableLiveData<List<Result>?>
        get() = _movies

    init {
        movieDao = MovieDatabase.getDatabase(context).movieDao()
    }

    fun getPosts(session : String){
        launch {
            _loadingState.value = MovieViewModel.State.ShowLoading
            val list = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitInstance.getPostApi().getFavorites(session_id = session)
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

            _loadingState.value = MovieViewModel.State.HideLoading
            _loadingState.value = MovieViewModel.State.Finish
        }

    }

}