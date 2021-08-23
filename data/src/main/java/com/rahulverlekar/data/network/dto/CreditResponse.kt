package com.rahulverlekar.data.entities

import com.google.gson.annotations.SerializedName

data class CreditResponse(
	@SerializedName("id") val id: Int? = null,
	@SerializedName("cast") val cast: List<CastEntity>? = null,
	@SerializedName("crew") val crew: List<CrewEntity>? = null,
)

data class CastEntity(
	@SerializedName("id") val id: String? = null,
	@SerializedName("name") val name: String? = null,
	@SerializedName("popularity") val popularity: Number? = null,
	@SerializedName("character") val character: String? = null
)

data class CrewEntity(
	@SerializedName("id") val id: String? = null,
	@SerializedName("name") val name: String? = null,
	@SerializedName("popularity") val popularity: Number? = null,
	@SerializedName("job") val job: String? = null
)
