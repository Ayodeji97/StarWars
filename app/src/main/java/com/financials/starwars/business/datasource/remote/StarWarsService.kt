package com.financials.starwars.business.datasource.remote

import com.financials.starwars.business.datasource.remote.model.*
import com.financials.starwars.business.datasource.remote.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsService {

    @GET("people/")
    suspend fun searchCharacters(@Query("search") params: String): Response<CharacterSearchResponse>

    @GET
    suspend fun getCharacterDetail(@Url url: String): Response<CharacterResponse>

    @GET
    suspend fun getSpecieDetail(@Url url: String): Response<SpecieResponse>

    @GET
    suspend fun getFilmDetail(@Url url: String): Response<FilmResponse>

    @GET
    suspend fun getPlanetDetail(@Url url: String): Response<PlanetResponse>

}