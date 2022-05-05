package com.example.movieforchill.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Session(

    @SerializedName("session_id")
    @Expose
    var session_id: String = ""
)