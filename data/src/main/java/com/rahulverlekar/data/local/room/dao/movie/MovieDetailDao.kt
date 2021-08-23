package com.rahulverlekar.data.local.room.dao.movie

import androidx.room.*
import com.rahulverlekar.data.local.room.model.movie.MovieDetailDB

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: MovieDetailDB)

    @Update
    suspend fun update(vararg obj: MovieDetailDB)

    @Delete
    suspend fun delete(vararg obj: MovieDetailDB)

    @Query("SELECT * FROM moviedetaildb WHERE id = :id")
    suspend fun load(id: Int): MovieDetailDB

    @Query("SELECT COUNT(*) FROM moviedetaildb WHERE id = :id")
    suspend fun hasDetail(id: Int): Int

    @Query("SELECT * from moviedetaildb WHERE id IN (:ids)")
    suspend fun loadAll(ids: List<String>): List<MovieDetailDB>
}