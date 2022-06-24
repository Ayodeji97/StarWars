package com.financials.starwars.business.datasource.remote.remotesource

import com.financials.starwars.business.datasource.cache.model.CharacterDetailEntity
import com.financials.starwars.business.datasource.cache.model.PlanetEntity
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.datasource.remote.response.CharacterResponse
import com.financials.starwars.business.datasource.remote.response.FilmResponse
import com.financials.starwars.business.datasource.remote.response.PlanetResponse
import com.financials.starwars.business.datasource.remote.response.SpecieResponse
import com.financials.starwars.business.utils.Result

interface CharacterDetailRemoteSource {

    suspend fun getCharacter(characterUrl: String): Result<CharacterResponse>

    suspend fun getPlanet(planetUrl: String): Result<PlanetResponse>

    suspend fun getSpecie(specieUrl: String): Result<SpecieResponse>

    suspend fun getFilm(filmUrl: String): Result<FilmResponse>
}