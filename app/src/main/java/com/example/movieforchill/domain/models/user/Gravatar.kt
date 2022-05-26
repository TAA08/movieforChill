package com.example.movieforchill.domain.models.user


import com.google.gson.annotations.SerializedName

data class Gravatar(
    @SerializedName("hash")
    val hash: String
)