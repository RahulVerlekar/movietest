package com.rahulverlekar.domain.usecase.movies

import com.rahulverlekar.domain.model.config.MovieDbConfig
import com.rahulverlekar.domain.model.movie.Movie
import com.rahulverlekar.domain.model.movie.MovieDetail
import com.rahulverlekar.domain.model.movie.Review

interface MovieUseCase {
    suspend fun getMovies(): List<Movie>

    suspend fun getMovie(id: Int): MovieDetail

    suspend fun getReviews(id: Int): List<Review>?

    suspend fun getSimilar(id: Int): List<Movie>?

    suspend fun getConfig(): MovieDbConfig
}