package com.financials.starwars.business.datasource.remote.remotesource.charactersearch

import android.util.Log
import com.financials.starwars.business.datasource.remote.StarWarsService
import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.utils.Result
import com.financials.starwars.di.dispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class CharacterSearchRemoteSourceImpl @Inject constructor(
    private val starWarsService: StarWarsService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CharacterSearchRemoteSource {
    override suspend fun charactersSearch(characterName: String): Result<CharacterSearchDto> =
            withContext(ioDispatcher) {
                return@withContext try {
                    val apiResponse = starWarsService.searchCharacters(characterName)
                    Result.Loading(true)
                    if (apiResponse.isSuccessful) {
                        Result.Loading(true)
                        val searchCharacters = apiResponse.body()
                        Result.Success(searchCharacters)
                    } else {
                        Result.Success(null)
                    }
                } catch (exception: Exception) {
                    Result.Error(exception)
                }
            }

}