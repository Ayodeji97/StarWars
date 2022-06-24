package com.financials.starwars.business.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class FilmResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("opening_crawl")
    val openingCrawl: String
)
