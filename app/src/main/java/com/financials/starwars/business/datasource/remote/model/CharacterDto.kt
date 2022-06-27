package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("birth_year")
    val birthYear: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    @SerializedName("species")
    val species: List<String>,
    @SerializedName("films")
    val films: List<String>,
    @SerializedName("url")
    val url: String
)
