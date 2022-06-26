package com.financials.starwars.business.repository.characterdetail

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.domain.model.Character

import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {

    suspend fun getCharacter(characterUrl: String): Flow<Result<CharacterDto>>

    suspend fun getPlanet(planetUrl: String): Flow<Result<PlanetDto>>

    suspend fun getSpecie(specieUrl: String): Flow<Result<SpecieDto>>

    suspend fun getFilm(filmUrl: String): Flow<Result<FilmDto>>
}