package com.financials.starwars.business.repository.characterdetail

import android.util.Log
import com.financials.starwars.business.datasource.remote.model.*
import com.financials.starwars.business.datasource.remote.remotesource.characterdetail.CharacterDetailRemoteSource
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.utils.Result
import com.financials.starwars.di.dispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val characterDetailRemoteSource: CharacterDetailRemoteSource,
) : CharacterDetailRepository {

    override suspend fun getCharacter(characterUrl: String): Flow<Result<CharacterDto>> =
        flow {
            when(val response = characterDetailRemoteSource.getCharacter(characterUrl)) {

                is Result.Success -> {
                    if (response.data != null) {
                        emit(Result.Success(response.data))
                    } else {
                       emit(Result.Success(null))
                    }
                }
                is Result.Error -> {
                    emit(Result.Error(response.exception))
                }
                else -> emit(Result.Loading)
            }
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