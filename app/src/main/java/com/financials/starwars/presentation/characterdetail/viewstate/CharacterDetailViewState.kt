package com.financials.starwars.presentation.characterdetail.viewstate

import com.financials.starwars.business.domain.model.CharacterDetail

sealed class CharacterDetailViewState {
    data class IsLoading(
        val isLoading: Boolean
    ) : CharacterDetailViewState()

    data class Success(
        val character: CharacterDetail?
    ) : CharacterDetailViewState()

    data class Error(
        val error: String
    ) : CharacterDetailViewState()
}
