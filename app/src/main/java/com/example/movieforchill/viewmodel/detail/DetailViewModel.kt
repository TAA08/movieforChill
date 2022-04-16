package com.example.movieforchill.viewmodel.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieforchill.model.Result
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.viewmodel.main.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val _liveDataDetail = MutableLiveData<StateDetail>()
    val liveDataDetail: LiveData<StateDetail>
        get() = _liveDataDetail


    /*private fun getMovieDetails(){
        launch {
            result = args.result
            var response = RetrofitInstance.getMovieDetail().getMovieDetail(id = result.id)

        }
    }*/

    sealed class StateDetail {
        object ShowLoading : StateDetail()
        object HideLoading : StateDetail()
        data class ResultsDetail(val list: Result) : StateDetail()
    }
}