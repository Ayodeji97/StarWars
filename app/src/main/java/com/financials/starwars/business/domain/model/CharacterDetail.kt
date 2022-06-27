package com.financials.starwars.business.domain.model

data class CharacterDetail(
    val films: List<String>,
    val planet: String,
    val species: List<String>,
    val url: String
)
