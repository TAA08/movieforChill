package com.example.movieforchill.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class MovieUpdate(

    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)