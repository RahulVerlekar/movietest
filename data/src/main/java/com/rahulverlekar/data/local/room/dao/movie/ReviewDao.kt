package com.rahulverlekar.data.local.room.dao.movie

import androidx.room.*
import com.rahulverlekar.data.local.room.model.movie.ReviewDB

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: ReviewDB)

    @Update
    suspend fun update(vararg obj: ReviewDB)

    @Delete
    suspend fun delete(vararg obj: ReviewDB)

    @Query("SELECT * FROM reviewdb WHERE id = :id")
    suspend fun load(id: Int): ReviewDB

    @Query("SELECT * from reviewdb WHERE movieId = :movieId")
    suspend fun loadAll(movieId: Int): List<ReviewDB>
}