package com.example.movieforchill.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.model.room.repository.LoginRepository
import kotlinx.coroutines.launch

class MainViewModel(val repository: LoginRepository) : ViewModel() {

    fun deleteSession() {

        viewModelScope.launch {
            repository.deleteSession()
        }
    }
}