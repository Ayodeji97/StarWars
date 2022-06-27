package com.financials.starwars.business.datasource.remote

import com.financials.starwars.business.datasource.remote.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsService {

    @GET("people/")
    suspend fun searchCharacters(@Query("search") params: String):
            Response<CharacterSearchDto>

    @GET
    suspend fun getCharacterDetail(@Url url: String): Response<CharacterDto>

    @GET
    suspend fun getSpecieDetail(@Url url: String): Response<SpecieDto>

    @GET
    suspend fun getFilmDetail(@Url url: String): FilmDto

    @GET
    suspend fun getPlanetDetail(@Url url: String): Response<PlanetDto>
}