package com.example.movieforchill.data.room

import androidx.room.*
import com.example.movieforchill.domain.models.movie.Result

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

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUser(user: DbAccountDetails)
//
//    @Query("SELECT * FROM users_table WHERE id == :userId")
//    suspend fun getUserById(userId: Int): DbAccountDetails

//    @Update(entity = DbAccountDetails::class)
//    suspend fun userUpdate(user: AccountUpdate)
}