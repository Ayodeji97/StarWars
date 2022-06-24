package com.financials.starwars.business.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class SpecieResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    @SerializedName("language")
    val language: String,
)
