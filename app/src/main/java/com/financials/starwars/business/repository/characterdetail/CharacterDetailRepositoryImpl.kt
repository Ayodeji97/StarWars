package com.financials.starwars.business.repository.characterdetail

import com.financials.starwars.business.datasource.remote.model.*
import com.financials.starwars.business.datasource.remote.remotesource.characterdetail.CharacterDetailRemoteSource
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.domain.model.Planet
import com.financials.starwars.business.utils.Result
import com.financials.starwars.business.utils.mapper.remotemapper.CharacterDetailDtoMapper
import com.financials.starwars.business.utils.mapper.remotemapper.FilmDtoMapper
import com.financials.starwars.business.utils.mapper.remotemapper.PlanetDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val characterDetailRemoteSource: CharacterDetailRemoteSource,
    private val characterDetailDtoMapper: CharacterDetailDtoMapper,
    private val planetDtoMapper: PlanetDtoMapper,
    private val filmDtoMapper: FilmDtoMapper
) : CharacterDetailRepository {

    override suspend fun getCharacter(characterUrl: String): Flow<Result<CharacterDetail>> =
        flow {
            when (val response = characterDetailRemoteSource.getCharacter(characterUrl)) {

                is Result.Success -> {
                    if (response.data != null) {
                        emit(Result.Success(characterDetailDtoMapper.transformToDomain(response.data)))
                    } else {
                        emit(Result.Success(null))
                    }
                }
                is Result.Error -> {
                    emit(Result.Error(response.exception))
                }
                else -> emit(Result.Loading(true))
            }
        }


    override suspend fun getPlanet(planetUrl: String): Flow<Result<Planet>> =
        flow {
            when(val response = characterDetailRemoteSource.getPlanet(planetUrl)) {

                is Result.Success -> {
                    if (response.data != null) {
                        emit(Result.Success(planetDtoMapper.transformToDomain(response.data)))
                    } else {
                        emit(Result.Success(null))
                    }
                }
                is Result.Error -> {
                    emit(Result.Error(response.exception))
                }
                else -> emit(Result.Loading(true))
            }
        }

    override suspend fun getFilm(filmUrl: List<String>): Flow<Result<List<Film>>> =
        flow {
            when(val response = characterDetailRemoteSource.getFilm(filmUrl)) {
                is Result.Loading -> TODO()
                is Result.Success -> {
                   emit(Result.Success(response.data?.let { filmDtoMapper.transformToDomain(it) }))
                }
                is Result.Error -> {
                    emit(Result.Error(exception = response.exception))
                }
            }
        }

//    override suspend fun getFilm(filmUrl: List<String>): Flow<Result<List<Film>>> =
//        flow {
//            when (val response = characterDetailRemoteSource.getFilm(filmUrl)) {
//                is Result.Success -> {
//                    if (response.data != null) {
//                        emit(Result.Success(filmDtoMapper.transformToDomain(response.data)))
//                    } else {
//                        emit(Result.Success(null))
//                    }
//                }
//                is Result.Error -> {
//                    Result.Error(response.exception)
//                }
//            }
//        }



    override suspend fun getSpecie(specieUrl: String): Flow<Result<SpecieDto>> {
        TODO("Not yet implemented")
    }


}