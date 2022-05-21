package com.example.movieforchill.model.account


import com.google.gson.annotations.SerializedName

data class AvatarX(
    @SerializedName("gravatar")
    val gravatar: Gravatar,
    @SerializedName("tmdb")
    val tmdb: Tmdb
)