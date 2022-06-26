package com.financials.starwars.presentation.charactersearch


sealed class CharacterSearchViewEvent {

    data class GetCharacterBySearch(
        val query: String
    ) : CharacterSearchViewEvent()

}