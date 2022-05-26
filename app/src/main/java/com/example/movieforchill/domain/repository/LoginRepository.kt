package com.example.movieforchill.domain.repository

interface LoginRepository {


    suspend fun deleteSession()

    suspend fun login(username: String, password: String): String
}