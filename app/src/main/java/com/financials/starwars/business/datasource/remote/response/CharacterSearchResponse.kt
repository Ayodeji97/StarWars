package com.financials.starwars.business.datasource.remote.response

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.google.gson.annotations.SerializedName

data class CharacterSearchResponse(
    @SerializedName("results")
    val results : List<CharacterDto>
)
