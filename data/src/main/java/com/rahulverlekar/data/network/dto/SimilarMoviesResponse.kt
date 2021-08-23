package com.rahulverlekar.data.entities

import com.google.gson.annotations.SerializedName

data class SimilarMoviesResponse(
	val page: Int? = null,
	@SerializedName("total_pages") val totalPages: Int? = null,
	val results: List<MovieEntity?>? = null,
	@SerializedName("total_results") val totalResults: Int? = null
)


