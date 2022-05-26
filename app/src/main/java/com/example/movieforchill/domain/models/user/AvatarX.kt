package com.example.movieforchill.domain.models.user


import com.google.gson.annotations.SerializedName

data class AvatarX(
    @SerializedName("gravatar")
    val gravatar: Gravatar,
    @SerializedName("tmdb")
    val tmdb: Tmdb
)