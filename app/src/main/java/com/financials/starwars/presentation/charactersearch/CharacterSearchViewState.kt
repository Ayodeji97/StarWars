package com.financials.starwars.presentation.charactersearch

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.domain.model.Character

sealed class CharacterSearchViewState {

    data class IsLoading(
        val isLoading: Boolean = false
    ) : CharacterSearchViewState()

    data class IsEmpty(
        val isEmpty: Boolean = false
    ) : CharacterSearchViewState()

    data class Success(
        val charactersSearch: List<Character>?
    ) : CharacterSearchViewState()

    data class Error(
        val error: String
    ) : CharacterSearchViewState()


}
