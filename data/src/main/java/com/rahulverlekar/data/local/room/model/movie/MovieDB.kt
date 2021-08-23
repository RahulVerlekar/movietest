package com.rahulverlekar.data.local.room.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rahulverlekar.domain.model.movie.Movie

@Entity
data class MovieDB(@PrimaryKey val id:Int, val name: String, val poster: String, val releaseDate: String, val adult: Boolean)

fun MovieDB.toDomain(): Movie {
    return Movie(id, name, poster, releaseDate, adult)
}

fun Movie.fromDomain(): MovieDB {
    return MovieDB(id, name, poster, releaseDate, adult)
}