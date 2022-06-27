package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterSearchDto(
    @SerializedName("results")
    val results : List<CharacterDto>
)
