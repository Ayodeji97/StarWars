package com.financials.starwars.business.repository

import com.financials.starwars.business.datasource.utils.DummyData
import com.financials.starwars.business.datasource.utils.MainCoroutineRule
import com.financials.starwars.business.datasource.utils.TestConstants.CHARACTER_URL
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepositoryImpl
import com.financials.starwars.business.repository.fakes.fakedetail.FakeCharacterDetailRemoteSource
import com.financials.starwars.business.repository.fakes.fakedetail.FakeDetailRepository
import com.financials.starwars.business.repository.fakes.fakedetail.ResponseState
import com.financials.starwars.business.utils.Result
import com.financials.starwars.business.utils.mapper.remotemapper.CharacterDetailDtoMapper
import com.financials.starwars.business.utils.mapper.remotemapper.FilmDtoMapper
import com.financials.starwars.business.utils.mapper.remotemapper.PlanetDtoMapper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterDetailRepositoryTest {

    private lateinit var fakeCharacterDetailRepository : FakeDetailRepository
    private lateinit var fakeCharacterDetailRemoteSource: FakeCharacterDetailRemoteSource
    private lateinit var characterDetailRepository: CharacterDetailRepository
    private val characterDetailDtoMapper: CharacterDetailDtoMapper = CharacterDetailDtoMapper()
    private val planetDtoMapper: PlanetDtoMapper = PlanetDtoMapper()
    private val filmDtoMapper: FilmDtoMapper = FilmDtoMapper()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeCharacterDetailRemoteSource = FakeCharacterDetailRemoteSource()
        fakeCharacterDetailRepository = FakeDetailRepository()
        characterDetailRepository = CharacterDetailRepositoryImpl(fakeCharacterDetailRemoteSource,
            characterDetailDtoMapper, planetDtoMapper, filmDtoMapper)
    }

    @Test
    fun `check that calling getCharacter return correct data` () = runBlocking {
        fakeCharacterDetailRepository.characterResponseType = ResponseState.DATA
        val character = characterDetailRepository.getCharacter(CHARACTER_URL).first()
        when(character) {
            is Result.Success -> {
                assertThat(character.data?.films).isEmpty()
                assertThat(character.data?.url).isEqualTo("https://swapi.dev/test/23")
            }
        }
    }

    @Test
    fun `check that calling getFilm return correct data` () = runBlocking {
        fakeCharacterDetailRepository.filmResponseType = ResponseState.DATA
        val character = characterDetailRepository.getFilm(listOf("", "")).first()
        when(character) {
            is Result.Success -> {
                assertThat(character.data?.first()?.title).isNotEmpty()
                assertThat(character.data?.first()?.title).isEqualTo(DummyData.film.title)
                assertThat(character.data?.first()?.openingCrawl).isEqualTo(DummyData.film.openingCrawl)
            }
            else -> {}
        }
    }

    @Test
    fun `check that calling getPlanet return correct data` () = runBlocking {
        fakeCharacterDetailRepository.planetResponseType = ResponseState.DATA
        val character = characterDetailRepository.getPlanet("").first()
        when(character) {
            is Result.Success -> {
                assertThat(character.data?.name).isNotEmpty()
                assertThat(character.data?.name).isEqualTo(DummyData.planetDto.name)
                assertThat(character.data?.population).isEqualTo(DummyData.planetDto.population)
            }
            else -> {}
        }
    }
}