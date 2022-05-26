package com.example.movieforchill.domain.usecases

import com.example.movieforchill.domain.repository.LoginRepository

class UserLoginUseCase(val repository: LoginRepository) {

    suspend operator fun invoke(username: String, password: String) : String{
        return repository.login(username, password)
    }
}