package com.example.movieforchill.model.room.dao

import androidx.room.*
import com.example.movieforchill.model.models.MovieUpdate
import com.example.movieforchill.model.models.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Query("SELECT * FROM popular_movies_table")
    fun getAll(): List<Result>

    @Query("SELECT * FROM popular_movies_table WHERE id == :movieId")
    fun getMovieById(movieId : Int): Result

    @Update
    suspend fun updateState(movieId : Int)
}