package com.financials.starwars.business.repository.charactersearch

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow

interface CharactersSearchRepository {
    suspend fun charactersSearch (characterName : String) : Flow<Result<List<Character>>>
}