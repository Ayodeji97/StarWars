package com.financials.starwars.presentation.characterdetail.viewstate

import com.financials.starwars.business.domain.model.Planet

sealed class PlanetViewState {

    data class IsLoading(
        val isLoading: Boolean
    ) : PlanetViewState()

    data class Success(
        val planet: Planet?
    ) : PlanetViewState()

    data class Error(
        val error: String
    ) : PlanetViewState()

}

