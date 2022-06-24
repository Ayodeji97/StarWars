package com.financials.starwars.business.datasource.cache.model

import com.google.gson.annotations.SerializedName

data class SpecieEntity(
    val name: String,
    val homeWorld: String,
    val language: String,
)
