package com.rahulverlekar.data.local.room.dao.movie

import androidx.room.*
import com.rahulverlekar.data.local.room.model.movie.MovieDB


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: MovieDB)

    @Update
    suspend fun update(vararg obj: MovieDB)

    @Delete
    suspend fun delete(vararg obj: MovieDB)

    @Query("SELECT * FROM moviedb WHERE id = :id")
    suspend fun load(id: Int): MovieDB

    @Query("SELECT * from moviedb WHERE id IN (:ids)")
    suspend fun loadAll(ids: List<String>): List<MovieDB>

    @Query("SELECT * from moviedb")
    suspend fun loadAll(): List<MovieDB>

    @Query("SELECT COUNT(*) FROM moviedb")
    suspend fun getRowCount(): Int
}