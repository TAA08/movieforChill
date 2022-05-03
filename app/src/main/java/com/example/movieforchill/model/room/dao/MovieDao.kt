package com.example.movieforchill.model.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieforchill.model.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Query("SELECT * FROM popular_movie_table")
    fun getAll(): List<Result>

    @Query("SELECT * FROM popular_movie_table WHERE id == :movieId")
    fun getMovieById(movieId : Int): Result

//    @Query("SELECT * FROM popular_movie_table WHERE id = true")
//    fun getFavouriteMovie(movieId : Int): Result
}