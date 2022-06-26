package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class SpecieDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    @SerializedName("language")
    val language: String,
)
