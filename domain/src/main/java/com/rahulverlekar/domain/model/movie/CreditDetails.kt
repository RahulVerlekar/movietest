package com.rahulverlekar.domain.model.movie

data class CreditDetails(
    val crew: List<CrewMember>,
    val cast: List<CastMember>
)