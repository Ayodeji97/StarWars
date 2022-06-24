package com.financials.starwars.business.repository

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow

class CharacterDetailRepositoryImpl () : CharacterDetailRepository {
    override suspend fun getCharacter(characterUrl: String): Flow<Result<CharacterDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlanet(planetUrl: String): Flow<Result<PlanetDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSpecie(specieUrl: String): Flow<Result<SpecieDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilm(filmUrl: String): Flow<Result<FilmDto>> {
        TODO("Not yet implemented")
    }
}