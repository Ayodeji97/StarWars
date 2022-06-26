package com.financials.starwars.business.datasource.remote.remotesource.characterdetail

import com.financials.starwars.business.datasource.cache.model.CharacterDetailEntity
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.utils.Result

interface CharacterDetailRemoteSource {

    suspend fun getCharacter(characterUrl: String): Result<CharacterDto>

    suspend fun getPlanet(planetUrl: String): Result<PlanetDto>

    suspend fun getSpecie(specieUrl: String): Result<SpecieDto>

    suspend fun getFilm(filmUrl: String): Result<FilmDto>
}