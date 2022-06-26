package com.financials.starwars.presentation.characterdetail

import com.financials.starwars.business.datasource.remote.model.CharacterDto

sealed class CharacterDetailViewState {
    data class IsLoading(
        val isLoading: Boolean
    ) : CharacterDetailViewState()

    data class Success(
        val character: CharacterDto?
    ) : CharacterDetailViewState()

    data class Error(
        val error: String
    ) : CharacterDetailViewState()
}
