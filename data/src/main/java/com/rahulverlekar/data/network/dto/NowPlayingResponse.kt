package com.rahulverlekar.data.entities

import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(
	val dates: Dates? = null,
	val page: Int? = null,
	val totalPages: Int? = null,
	val results: List<MovieEntity?>? = null,
	val totalResults: Int? = null
)

data class Dates(
	val maximum: String? = null,
	val minimum: String? = null
)

data class MovieEntity(
	val overview: String? = null,
	@SerializedName("original_language") val originalLanguage: String? = null,
	@SerializedName("original_title") val originalTitle: String? = null,
	val video: Boolean? = null,
	val title: String? = null,
	val genreIds: List<Int?>? = null,
	@SerializedName("poster_path") val posterPath: String? = null,
	@SerializedName("backdrop_path") val backdropPath: String? = null,
	@SerializedName("release_date")  val releaseDate: String? = null,
	val popularity: Double? = null,
	val voteAverage: Double? = null,
	val id: Int? = null,
	val adult: Boolean? = null,
	val voteCount: Int? = null
)

