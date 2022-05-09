package com.example.movieforchill.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.model.LoginApprove
import com.example.movieforchill.model.Token
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String>
        get() = _sessionId

    fun login(data: LoginApprove) {

        viewModelScope.launch {
            _loadingState.value = LoadingState.ShowLoading
            val responseGet = RetrofitInstance.getPostApi().getToken()
            if (responseGet.isSuccessful) {
                val loginApprove = LoginApprove(
                    username = data.username,
                    password = data.password,
                    request_token = responseGet.body()?.request_token as String
                )
                val responseApprove = RetrofitInstance.getPostApi().approveToken(
                    loginApprove = loginApprove
                )
                if (responseApprove.isSuccessful) {
                    val session =
                        RetrofitInstance.getPostApi().createSession(
                            token = responseApprove.body() as Token
                        )
                    if (session.isSuccessful) {
                        _sessionId.value = session.body()?.session_id
                        _loadingState.value = LoadingState.HideLoading
                        _loadingState.value = LoadingState.Finish
                    }
                } else {
                    _loadingState.value = LoadingState.HideLoading
                }
            }
        }
    }


    sealed class LoadingState {
        object ShowLoading : LoadingState()
        object HideLoading : LoadingState()
        object Finish : LoadingState()
    }

}