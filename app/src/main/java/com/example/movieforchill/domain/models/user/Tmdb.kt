package com.example.movieforchill.domain.models.user


import com.google.gson.annotations.SerializedName

data class Tmdb(
    @SerializedName("avatar_path")
    val avatarPath: Any
)