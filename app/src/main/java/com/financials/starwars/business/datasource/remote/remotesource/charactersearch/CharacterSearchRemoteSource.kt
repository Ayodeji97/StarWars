package com.financials.starwars.business.datasource.remote.remotesource.charactersearch

import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow

interface CharacterSearchRemoteSource {
    suspend fun charactersSearch (characterName : String) : Result<CharacterSearchDto>
}