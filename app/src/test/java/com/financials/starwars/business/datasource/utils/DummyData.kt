package com.financials.starwars.business.datasource.utils

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.domain.model.Planet

object DummyData {

    const val name = "flmslm"
    const val characterUrl = "https://swapi.dev/test/100"


    val characterDto = CharacterDto(
        name = "Angel Great",
        birthYear = "407BCK",
        height = "180",
        url = "https://swapi.dev/test/23",
        homeWorld = "",
        species = listOf(),
        films = listOf()
    )

    val characterSearchDto by lazy {
        CharacterSearchDto(
            results = listOf(
                characterDto
            )
        )
    }


    val character = Character(
        name = "Angel Great",
        birthYear = "407BCK",
        height = "180",
        url = "https://swapi.dev/test/23"
    )

    val characterDetail = CharacterDetail(
        films = listOf("https://swapi.dev/api/filmstest/67", "https://swapi.dev/api/filmstest/98"),
        planet = "https://swapi.dev/api/planettest/",
        species = listOf(
            "https://swapi.dev/api/speciestest/67",
            "https://swapi.dev/api/speciestest/98"
        ),
        url = "https://swapi.dev/test/100"
    )

    val planet = Planet(
        name = "David Messi",
        population = "345000"
    )

    val planetDto = PlanetDto(
        name = "Ade John",
        population = "560000"
    )

    val film = Film(
        title = "Sangotedo",
        openingCrawl = "Once on upon a time, I can't do all this"
    )

    val filmDto = FilmDto(
        title = "Sangotedo",
        openingCrawl = "Once on upon a time, I can't do all this"
    )
}