package com.financials.starwars.business.datasource.remote.remotesource.characterdetail

import com.financials.starwars.business.datasource.remote.StarWarsService
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.utils.Result
import com.financials.starwars.business.utils.mapper.remotemapper.FilmDtoMapper
import com.financials.starwars.di.dispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

class CharacterDetailRemoteSourceImpl @Inject constructor(
    private val starWarsService: StarWarsService,
    private val filmDtoMapper: FilmDtoMapper,
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
            } catch (exception: Exception) {
                Result.Error(exception)
            }

        }


    override suspend fun getPlanet(planetUrl: String): Result<PlanetDto> =
        withContext(ioDispatcher) {
            return@withContext try {
                val apiResponse = starWarsService.getPlanetDetail(planetUrl)
                if (apiResponse.isSuccessful) {
                    val remotePlanet = apiResponse.body()
                    Result.Success(remotePlanet)
                } else {
                    Result.Success(null)
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }


    override suspend fun getFilm(filmUrls: List<String>): Result<List<FilmDto>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val filmDetails : List<FilmDto> = filmUrls.map {url ->
                    starWarsService.getFilmDetail(url)
                }
                Result.Success(filmDetails)
            }
            catch (exception : Exception) {
                Result.Error(exception)
            }
        }



    override suspend fun getSpecie(specieUrl: String): Result<SpecieDto> {
        TODO("Not yet implemented")
    }


}