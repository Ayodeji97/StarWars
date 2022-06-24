package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    val name: String,
    val birthYear: String,
    val height: String,
    val url: String
)
