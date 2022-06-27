package com.financials.starwars.presentation.characterdetail.viewstate

import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.presentation.charactersearch.CharacterSearchViewState

sealed class FilmViewState {
    data class IsLoading(
        val isLoading: Boolean
    ) : FilmViewState()

    data class IsEmpty(
        val isEmpty: Boolean = false
    ) : FilmViewState()

    data class Success(
        val film: List<Film>?
    ) : FilmViewState()

    data class Error(
        val error: String
    ) : FilmViewState()
}
