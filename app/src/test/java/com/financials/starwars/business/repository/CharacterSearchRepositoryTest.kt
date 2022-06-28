package com.financials.starwars.business.repository

import com.financials.starwars.business.datasource.utils.DummyData
import com.financials.starwars.business.datasource.utils.MainCoroutineRule
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepository
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepositoryImpl
import com.financials.starwars.business.utils.Result
import com.financials.starwars.business.utils.mapper.remotemapper.CharacterDtoMapper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterSearchRepositoryTest {


    private lateinit var fakeCharactersSearchRepository: FakeSearchRepository
    private lateinit var fakeRemoteSource: FakeRemoteSource
    private lateinit var charactersSearchRepository: CharactersSearchRepository
    private val characterDtoMapper: CharacterDtoMapper = CharacterDtoMapper()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeRemoteSource = FakeRemoteSource()
        fakeCharactersSearchRepository = FakeSearchRepository()
        charactersSearchRepository = CharactersSearchRepositoryImpl(fakeRemoteSource, characterDtoMapper)
    }

    @Test
    fun `check that calling searchCharacters return correct data` () = runBlocking {
        fakeCharactersSearchRepository.responseType = ResponseState.DATA
        val charactersResult  = charactersSearchRepository.charactersSearch(DummyData.character.name).first()
        when(charactersResult) {
            is Result.Success -> {
                val character : Character = charactersResult.data?.first()!!
                assertThat(character.name).isEqualTo(DummyData.character.name)
                assertThat(character.birthYear).isEqualTo(DummyData.character.birthYear)
                assertThat(character.height).isEqualTo(DummyData.character.height)
                assertThat(character.url).isEqualTo(DummyData.character.url)
            }
        }
    }

    @Test
    fun `check that calling searchCharacters return list of characters` () = runBlocking {
        fakeCharactersSearchRepository.responseType = ResponseState.DATA

        val charactersResult  = charactersSearchRepository.charactersSearch(DummyData.character.name).first()
        when (charactersResult) {
            is Result.Success -> {
                assertThat(charactersResult.data?.size).isAtLeast(1)
            }
        }
    }

    @Test
    fun `check that calling charactersSearch return empty list when no data found` () = runBlocking {
        fakeCharactersSearchRepository.responseType = ResponseState.EMPTY
        val charactersResult  = charactersSearchRepository.charactersSearch(DummyData.name).first()

        when(charactersResult) {
            is Result.Success -> {
                assertThat(charactersResult.data?.first()?.name).doesNotMatch(DummyData.name)
            }
        }
    }
}