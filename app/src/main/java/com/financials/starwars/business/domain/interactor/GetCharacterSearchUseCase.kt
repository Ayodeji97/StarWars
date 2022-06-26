package com.financials.starwars.business.domain.interactor

import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepository
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterSearchUseCase @Inject constructor(
    private val charactersSearchRepository: CharactersSearchRepository
) {
    suspend operator fun invoke(characterName: String): Flow<Result<List<Character>>> {
        return charactersSearchRepository.charactersSearch(characterName)
    }
}