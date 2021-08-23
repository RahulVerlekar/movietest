package com.rahulverlekar.data.local.room.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rahulverlekar.domain.model.movie.Review

@Entity
data class ReviewDB(
    @PrimaryKey
    val id: String,
    val author: String,
    val content: String,
    val url: String,
    val movieId: String
)

fun ReviewDB.toDomain(): Review {
    return Review(id, author, content, url)
}

fun Review.fromDomain(movieId: String): ReviewDB {
    return ReviewDB(id, author, content, url, movieId)
}