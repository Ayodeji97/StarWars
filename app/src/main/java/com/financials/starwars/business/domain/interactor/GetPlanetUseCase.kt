package com.financials.starwars.business.domain.interactor

import com.financials.starwars.business.domain.model.Planet
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanetUseCase @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository

) {
    suspend operator fun invoke(planetUrl: String): Flow<Result<Planet>> {
        return characterDetailRepository.getPlanet(planetUrl)
    }
}