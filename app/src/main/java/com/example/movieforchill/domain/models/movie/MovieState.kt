package com.example.movieforchill.domain.models.movie

import com.google.gson.annotations.SerializedName

data class MovieState(
    @SerializedName("id")
    val id: Int,
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("rated")
    val rated: Any,
    @SerializedName("watchlist")
    val watchlist: Boolean
)
