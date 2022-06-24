package com.financials.starwars.business.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class PlanetResponse(
    @SerializedName("name")
    val name : String,
    @SerializedName("population")
    val population : String,
)
