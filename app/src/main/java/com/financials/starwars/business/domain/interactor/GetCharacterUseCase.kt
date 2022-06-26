package com.financials.starwars.business.domain.interactor

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository
)  {
    suspend operator fun invoke (characterUrl : String) : Flow<Result<CharacterDto>> {
        return characterDetailRepository.getCharacter(characterUrl)
    }
}