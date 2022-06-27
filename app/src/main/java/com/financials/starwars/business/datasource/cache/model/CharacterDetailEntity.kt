package com.financials.starwars.business.datasource.cache.model

data class CharacterDetailEntity(
    val films: List<String>,
    val planet: String,
    val species: List<String>,
    val url: String
)
