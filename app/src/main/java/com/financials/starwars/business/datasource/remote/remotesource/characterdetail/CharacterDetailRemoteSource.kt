package com.financials.starwars.business.datasource.remote.remotesource.characterdetail


import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.utils.Result

interface CharacterDetailRemoteSource {

    suspend fun getCharacter(characterUrl: String): Result<CharacterDto>

    suspend fun getFilm(filmUrls: List<String>): Result<List<FilmDto>>

    suspend fun getPlanet(planetUrl: String): Result<PlanetDto>

}