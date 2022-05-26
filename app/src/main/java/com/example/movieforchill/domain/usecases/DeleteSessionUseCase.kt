package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.repository.LoginRepository

class DeleteSessionUseCase(val repository: LoginRepository) {

    suspend operator fun invoke(){
        repository.deleteSession()
    }
}