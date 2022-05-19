package com.example.movieforchill.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.model.room.repository.LoginRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = LoginRepository(application)

    fun deleteSession() {

        viewModelScope.launch {
            repository.deleteSession()
        }
    }
}