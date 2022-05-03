package com.example.movieforchill.model.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieforchill.model.models.Result
import com.example.movieforchill.model.room.dao.MovieDao

@Database(entities = [Result::class], version = 1)
abstract class MovieDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object {

        var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "app_database.db3"
                ).build()
            }
            return INSTANCE!!
        }

    }
}