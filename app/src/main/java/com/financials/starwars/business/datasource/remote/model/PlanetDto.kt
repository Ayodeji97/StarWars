package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class PlanetDto(
    @SerializedName("name")
    val name : String,
    @SerializedName("population")
    val population : String,
)
