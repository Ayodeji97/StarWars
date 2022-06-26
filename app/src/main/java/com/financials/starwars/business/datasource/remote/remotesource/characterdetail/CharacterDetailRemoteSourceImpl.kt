package com.financials.starwars.business.datasource.remote.remotesource.characterdetail

import com.financials.starwars.business.datasource.remote.StarWarsService
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.datasource.remote.remotesource.characterdetail.CharacterDetailRemoteSource
import com.financials.starwars.business.utils.Result
import com.financials.starwars.di.dispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class CharacterDetailRemoteSourceImpl @Inject constructor(
    private val starWarsService: StarWarsService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CharacterDetailRemoteSource {

    override suspend fun getCharacter(characterUrl: String): Result<CharacterDto> =
        withContext(ioDispatcher) {
            return@withContext try {
                val apiResponse = starWarsService.getCharacterDetail(characterUrl)
                if (apiResponse.isSuccessful) {
                    val remoteCharacter = apiResponse.body()
                    Result.Success(remoteCharacter)
                } else {
                    Result.Success(null)
                }
            } catch (exception : Exception) {
                Result.Error(exception)
            }

        }

    override suspend fun getPlanet(planetUrl: String): Result<PlanetDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getSpecie(specieUrl: String): Result<SpecieDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilm(filmUrl: String): Result<FilmDto> {
        TODO("Not yet implemented")
    }
}