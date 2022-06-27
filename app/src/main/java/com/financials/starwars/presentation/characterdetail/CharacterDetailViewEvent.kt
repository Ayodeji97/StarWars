package com.financials.starwars.presentation.characterdetail

import com.financials.starwars.presentation.charactersearch.CharacterSearchViewEvent

sealed class CharacterDetailViewEvent {
    data class GetCharacter(
        val characterUrl: String
    ) : CharacterDetailViewEvent()

    data class GetFilm(
        val filmUrl: List<String>
    ) : CharacterDetailViewEvent()

    data class GetPlanet(
        val planetUrl: String
    ) : CharacterDetailViewEvent()
}
