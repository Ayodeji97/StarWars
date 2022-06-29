package com.financials.starwars.business.repository.fakes.fakedetail

import com.financials.starwars.business.datasource.utils.DummyData
import com.financials.starwars.business.datasource.utils.TestConstants.TEST_ERROR
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.domain.model.Planet
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepository
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeDetailRepository : CharacterDetailRepository {

    val error_text = TEST_ERROR
    val exception = Exception(error_text)

    private var characterFlowResult: Flow<Result<CharacterDetail>> =
        flowOf(Result.Success(DummyData.characterDetail))

    private var planetFlowResult: Flow<Result<Planet>> =
        flowOf(Result.Success(DummyData.planet))

    private var filmFlowResult: Flow<Result<List<Film>>> =
        flowOf(Result.Success(listOf(DummyData.film)))


    var characterResponseType: ResponseState = ResponseState.DATA
        set(value) {
            field = value
            characterFlowResult = createCharacterResponse(value)
        }

    var planetResponseType: ResponseState = ResponseState.DATA
        set(value) {
            field = value
            characterFlowResult = createCharacterResponse(value)
        }

    var filmResponseType: ResponseState = ResponseState.DATA
        set(value) {
            field = value
            filmFlowResult = filmCharacterResponse(value)
        }

    private fun createCharacterResponse(responseState: ResponseState): Flow<Result<CharacterDetail>> {
        return when (responseState) {
            ResponseState.DATA -> flowOf(Result.Success(DummyData.characterDetail))
            ResponseState.EMPTY -> flowOf(Result.Success(null))
            ResponseState.ERROR -> flowOf(Result.Error(exception))
        }
    }

    private fun planetCharacterResponse(responseState: ResponseState): Flow<Result<Planet>> {
        return when (responseState) {
            ResponseState.DATA -> flowOf(Result.Success(DummyData.planet))
            ResponseState.EMPTY -> flowOf(Result.Success(null))
            ResponseState.ERROR -> flowOf(Result.Error(exception))
        }
    }

    private fun filmCharacterResponse(responseState: ResponseState): Flow<Result<List<Film>>> {
        return when (responseState) {
            ResponseState.DATA -> flowOf(Result.Success(listOf(DummyData.film)))
            ResponseState.EMPTY -> flowOf(Result.Success(null))
            ResponseState.ERROR -> flowOf(Result.Error(exception))
        }
    }


    override suspend fun getCharacter(characterUrl: String): Flow<Result<CharacterDetail>> {
        return characterFlowResult
    }


    override suspend fun getFilm(filmUrl: List<String>): Flow<Result<List<Film>>> {
        return filmFlowResult
    }

    override suspend fun getPlanet(planetUrl: String): Flow<Result<Planet>> {
        return planetFlowResult
    }

}

enum class ResponseState {
    DATA,
    ERROR,
    EMPTY
}


