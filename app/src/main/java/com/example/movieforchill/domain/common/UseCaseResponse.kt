package com.example.movieforchill.domain.common

import com.example.movieforchill.domain.models.api_error.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}
