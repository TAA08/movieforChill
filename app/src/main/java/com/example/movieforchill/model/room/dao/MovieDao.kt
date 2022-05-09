package com.example.movieforchill.model.room.dao

import androidx.room.*
import com.example.movieforchill.model.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Query("SELECT * FROM popular_movie_table")
    fun getAll(): List<Result>

    @Query("SELECT * FROM popular_movie_table WHERE id == :movieId")
    fun getMovieById(movieId: Int): Result

    @Query("SELECT ALL * FROM popular_movie_table WHERE favouriteState == 1 ")
    fun getFavouriteMovies(): List<Result>

    @Update
    suspend fun updateState(movie: Result)
}