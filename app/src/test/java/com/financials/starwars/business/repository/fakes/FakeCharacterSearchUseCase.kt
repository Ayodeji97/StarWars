package com.financials.starwars.business.repository.fakes

import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow

class FakeCharacterSearchUseCase {
    private val fakeSearchRepository = FakeSearchRepository()
    suspend fun invoke(characterName: String): Flow<Result<List<Character>>> {
        return fakeSearchRepository.charactersSearch(characterName)
    }
}