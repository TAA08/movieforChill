package com.example.movieforchill.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Session(
    @SerializedName("session_id")
    @Expose
    var session_id: String = ""
)