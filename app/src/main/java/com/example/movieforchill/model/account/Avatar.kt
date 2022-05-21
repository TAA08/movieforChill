package com.example.movieforchill.model.account


import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("avatar")
    val avatar: AvatarX,
    @SerializedName("id")
    val id: Int,
    @SerializedName("include_adult")
    val includeAdult: Boolean,
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String
)