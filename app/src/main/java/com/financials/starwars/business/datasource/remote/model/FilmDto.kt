package com.financials.starwars.business.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("opening_crawl")
    val openingCrawl: String
)
