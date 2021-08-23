package com.rahulverlekar.data.caching

import com.rahulverlekar.data.local.room.AppDatabase
import com.rahulverlekar.data.local.room.model.movie.fromDomain
import com.rahulverlekar.data.local.room.model.movie.toDomain
import com.rahulverlekar.data.network.repository.MovieRepositoryImpl
import com.rahulverlekar.data.temporary.KeyValueWithPref
import com.rahulverlekar.domain.model.config.MovieDbConfig
import com.rahulverlekar.domain.model.movie.Movie
import com.rahulverlekar.domain.model.movie.MovieDetail
import com.rahulverlekar.domain.model.movie.Review
import com.rahulverlekar.domain.usecase.movies.MovieUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class MovieUseCasesCachedImpl @Inject constructor(
    private val remote: MovieRepositoryImpl,
    private val db: AppDatabase,
    private val keyVal: KeyValueWithPref
) : MovieUseCase {

    val movieDao by lazy {
        db.movieDao()
    }
    val movieDetailDoa by lazy {
        db.movieDetailDao()
    }
    val reviewDao by lazy {
        db.reviewDao()
    }

    override suspend fun getMovies(): List<Movie> {
        return if (movieDao.getRowCount() > 0) {
            movieDao.loadAll().map { it.toDomain() }
        } else {
            val movies = remote.getMovies()
            movieDao.insert(*movies.map { it.fromDomain() }.toTypedArray())
            movies
        }
    }

    override suspend fun getMovie(id: Int): MovieDetail {
        return if (movieDetailDoa.hasDetail(id) > 0) {
            movieDetailDoa.load(id).toDomain()
        } else {
            val movie = remote.getMovie(id)
            movieDetailDoa.insert(movie.fromDomain())
            movie
        }
    }

    override suspend fun getReviews(id: Int): List<Review>? {
        return if (movieDetailDoa.hasDetail(id) > 0) {
            reviewDao.loadAll(id).map { it.toDomain() }
        } else {
            val reviews = remote.getReviews(id)
            reviews?.map { it.fromDomain(id.toString()) }?.let { reviewDao.insert(*it.toTypedArray()) }
            reviews
        }
    }

    override suspend fun getSimilar(id: Int): List<Movie>? {
        return remote.getSimilar(id)
    }

    override suspend fun getConfig(): MovieDbConfig {
        return if(keyVal.getConfig() == null) {
            remote.getConfig()
        }
        else {
            keyVal.getConfig()!!
        }
    }
}