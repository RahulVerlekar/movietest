package com.rahulverlekar.data.local.room

import android.content.Context
import androidx.room.*
import com.rahulverlekar.data.local.room.dao.*
import com.rahulverlekar.data.local.room.dao.movie.MovieDao
import com.rahulverlekar.data.local.room.dao.movie.MovieDetailDao
import com.rahulverlekar.data.local.room.dao.movie.ReviewDao
import com.rahulverlekar.data.local.room.model.*
import com.rahulverlekar.data.local.room.model.movie.MovieDB
import com.rahulverlekar.data.local.room.model.movie.MovieDetailDB
import com.rahulverlekar.data.local.room.model.movie.ReviewDB

@Database(entities = arrayOf(
    MovieDB::class,
    MovieDetailDB::class,
    ReviewDB::class
), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    abstract fun movieDetailDao() : MovieDetailDao

    abstract fun reviewDao() : ReviewDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getClient(context: Context) : AppDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "Database")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}