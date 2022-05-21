package com.example.movieforchill.viewmodel.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.movieforchill.model.movie.LoginApprove
import com.example.movieforchill.model.room.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application,
    var repository: LoginRepository
) : ViewModel() {


    val context = application

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String>
        get() = _sessionId

    fun login(data: LoginApprove) {
        viewModelScope.launch {
            _loadingState.value = LoadingState.ShowLoading
            val session = repository.login(data.username, data.password)

            _sessionId.value = session
            _loadingState.value = LoadingState.HideLoading
            if (session.isNotBlank()){
                _loadingState.value = LoadingState.Finish
            }
            else{
                Toast.makeText(context, "Неверные данные", Toast.LENGTH_SHORT).show()
            }

        }
    }


    sealed class LoadingState {
        object ShowLoading : LoadingState()
        object HideLoading : LoadingState()
        object Finish : LoadingState()
    }

}