package com.financials.starwars.business.repository.characterdetail

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.domain.model.Planet

import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {

    suspend fun getCharacter(characterUrl: String): Flow<Result<CharacterDetail>>

    suspend fun getFilm(filmUrl: List<String>): Flow<Result<List<Film>>>

    suspend fun getPlanet(planetUrl: String): Flow<Result<Planet>>

    suspend fun getSpecie(specieUrl: String): Flow<Result<SpecieDto>>


}