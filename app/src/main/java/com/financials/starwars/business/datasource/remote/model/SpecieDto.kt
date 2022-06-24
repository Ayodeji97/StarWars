package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class SpecieDto(
    val name: String,
    val homeWorld: String,
    val language: String,
)
