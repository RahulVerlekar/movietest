package com.rahulverlekar.data.local.room.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rahulverlekar.domain.model.movie.MovieDetail

@Entity
class MovieDetailDB(
    @PrimaryKey
    val id: Int,
    val title: String,
    val synopsis: String,
    val poster: String,
    val backdrop: String,
    val releaseDate: String,
)

fun MovieDetailDB.toDomain(): MovieDetail {
    return MovieDetail(id, title, synopsis, poster, backdrop, releaseDate)
}

fun MovieDetail.fromDomain(): MovieDetailDB {
    return MovieDetailDB(id, title, synopsis, poster, backdrop, releaseDate)
}