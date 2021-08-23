package com.rahulverlekar.data.network.repository

import com.rahulverlekar.data.entities.*
import com.rahulverlekar.data.network.common.RetrofitClient
import com.rahulverlekar.domain.model.config.MovieDbConfig
import com.rahulverlekar.domain.model.movie.*
import com.rahulverlekar.domain.usecase.movies.MovieUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MovieRepositoryImpl @Inject constructor(client: RetrofitClient) : MovieUseCase {

    val api by lazy {
        client.build().create(MovieService::class.java)
    }

    override suspend fun getMovies(): List<Movie> {
        return api.getMovies().mapToResult(getConfig())
    }

    override suspend fun getMovie(id: Int): MovieDetail {
        return api.getMovie(id).mapToResult(getConfig())
    }

    override suspend fun getReviews(id: Int): List<Review>? {
        return api.getReviews(id).mapToResult()
    }

    override suspend fun getSimilar(id: Int): List<Movie>? {
        return api.getSimilar(id).mapToResult(getConfig())
    }

    override suspend fun getConfig(): MovieDbConfig {
        return api.getConfig().mapToResult()
    }

    interface MovieService {

        @GET(value = "movie/now_playing")
        suspend fun getMovies(): NowPlayingResponse

        @GET(value = "movie/{id}")
        suspend fun getMovie(@Path("id") id: Int): MovieDetailResponse

        @GET(value = "movie/{movie_id}/reviews")
        suspend fun getReviews(@Path("movie_id") id: Int): MovieReviewResponse

        @GET(value = "movie/{movie_id}/credits")
        suspend fun getCredits(@Path("movie_id") id: Int): CreditResponse

        @GET(value = "movie/{movie_id}/similar")
        suspend fun getSimilar(@Path("movie_id") id: Int): SimilarMoviesResponse

        @GET(value = "configuration")
        suspend fun getConfig(): ConfigResponse
    }
}

fun ConfigResponse.mapToResult(): MovieDbConfig {
    return MovieDbConfig(
        images?.baseUrl ?: "",
        images?.secureBaseUrl ?: "",
        images?.backdropSizes?.first() ?: "original"
    )
}

fun NowPlayingResponse.mapToResult(config: MovieDbConfig): List<Movie> {
    val data = results?.map { entity ->
        Movie(
            entity?.id ?: 0,
            entity?.title ?: "",
            config.attachBasePath(entity?.posterPath ?: ""),
            entity?.releaseDate ?: "",
            entity?.adult?:false
        )
    }
    return data ?: listOf()
}

fun String.toDate(): Date {
    return Date()
}

fun MovieDetailResponse.mapToResult(config: MovieDbConfig): MovieDetail {
    return MovieDetail(
        id ?: 0,
        title ?: "",
        overview ?: "",
        config.attachBasePath(poster ?: ""),
        config.attachBasePath(backdrop ?: ""),
        releaseDate ?: ""
    )
}

fun MovieReviewResponse.mapToResult(): List<Review>? {
    return results?.map { entity ->
        Review(
            entity.id ?: "",
            entity.author ?: "",
            entity.content ?: "",
            entity.url ?: ""
        )
    }
}

fun CreditResponse.mapToResult(): CreditDetails {
    val crew = crew?.map { entity ->
        CrewMember(entity.id ?: "", entity.name ?: "", entity.popularity ?: 0.0, entity.job ?: "")
    }
    val cast = cast?.map { entity ->
        CastMember(
            entity.id ?: "",
            entity.name ?: "",
            entity.popularity ?: 0.0,
            entity.character ?: ""
        )
    }
    return CreditDetails(crew ?: emptyList(), cast ?: emptyList())
}

fun SimilarMoviesResponse.mapToResult(config: MovieDbConfig): List<Movie> {
    val data = results?.map { entity ->
        Movie(
            entity?.id ?: 0,
            entity?.title ?: "",
            config.attachBasePath(entity?.posterPath ?: ""),
            entity?.releaseDate ?: "",
            entity?.adult?:false
        )
    }
    return data ?: listOf()
}