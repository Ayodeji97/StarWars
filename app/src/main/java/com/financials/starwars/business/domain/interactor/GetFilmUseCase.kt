package com.financials.starwars.business.domain.interactor

import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository
) {
    suspend operator fun invoke(filmUrl: List<String>): Flow<Result<List<Film>>> {
        return characterDetailRepository.getFilm(filmUrl)
    }
}