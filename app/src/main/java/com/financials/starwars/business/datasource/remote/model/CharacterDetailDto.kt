package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterDetailDto(
    val films: List<String>,
    val planet: String,
    val species: List<String>,
    val url: String
)
