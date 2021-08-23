package com.rahulverlekar.data.entities

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
	@SerializedName("id") val id: Int? = null,
	@SerializedName("page") val page: Int? = null,
	@SerializedName("total_pages") val totalPages: Int? = null,
	@SerializedName("total_results") val totalResults: Int? = null,
	@SerializedName("results") val results: List<ReviewEntity>? = null,
)

data class ReviewEntity(
	@SerializedName("id") val id: String? = null,
	@SerializedName("author") val author: String? = null,
	@SerializedName("content") val content: String? = null,
	@SerializedName("url") val url: String? = null
)
