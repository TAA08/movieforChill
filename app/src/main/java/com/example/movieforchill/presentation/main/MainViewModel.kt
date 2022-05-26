package com.example.movieforchill.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.domain.usecases.DeleteSessionUseCase
import kotlinx.coroutines.launch

class MainViewModel(val deleteSessionUseCase: DeleteSessionUseCase) : ViewModel() {

    fun deleteSession() {

        viewModelScope.launch {
            deleteSessionUseCase
        }
    }
}