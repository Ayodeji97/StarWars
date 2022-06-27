package com.financials.starwars.business.repository.charactersearch

import android.util.Log
import com.financials.starwars.business.datasource.remote.remotesource.charactersearch.CharacterSearchRemoteSource
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.utils.Result
import com.financials.starwars.business.utils.mapper.remotemapper.CharacterDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersSearchRepositoryImpl @Inject constructor(
    private val characterSearchRemoteSource: CharacterSearchRemoteSource,
    private val characterDtoMapper: CharacterDtoMapper
) : CharactersSearchRepository {
    override suspend fun charactersSearch(characterName: String): Flow<Result<List<Character>>> =
        flow {
            when (val response = characterSearchRemoteSource.charactersSearch(characterName)) {
                is Result.Success -> {
                    if (response.data != null) {
                        emit(Result.Success(characterDtoMapper.transformToDomain(response.data.results)))
                    } else {
                        emit(Result.Success(null))
                    }
                }
                is Result.Error -> {
                    emit(Result.Error(response.exception))
                }
            }
        }
}