package com.example.movieforchill.domain.models.session

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("request_token")
    @Expose
    val request_token: String
)