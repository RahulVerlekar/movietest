package com.rahulverlekar.data.entities

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
	@SerializedName("id") val id: Int? = null,
	@SerializedName("title") val title: String? = null,
	@SerializedName("overview") val overview: String? = null,
	@SerializedName("release_date") val releaseDate: String? = null,
	@SerializedName("backdrop_path") val backdrop: String? = null,
	@SerializedName("poster_path") val poster: String? = null,
)
