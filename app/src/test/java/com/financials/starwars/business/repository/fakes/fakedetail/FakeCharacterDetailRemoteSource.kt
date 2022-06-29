package com.financials.starwars.business.repository.fakes.fakedetail

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.datasource.remote.remotesource.characterdetail.CharacterDetailRemoteSource
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


class FakeCharacterDetailRemoteSource : CharacterDetailRemoteSource {

    val error_text = TEST_ERROR
    val exception = Exception(error_text)

    private var characterFlowResult: Result<CharacterDto> =
        Result.Success(DummyData.characterDto)

    private var planetFlowResult: Result<PlanetDto> =
        Result.Success(DummyData.planetDto)

    private var filmFlowResult: Result<List<FilmDto>> =
        Result.Success(listOf(DummyData.filmDto))


    var characterResponseType: ResponseStateRemote = ResponseStateRemote.DATA
        set(value) {
            field = value
            characterFlowResult = createCharacterResponse(value)
        }

    var planetResponseType: ResponseStateRemote = ResponseStateRemote.DATA
        set(value) {
            field = value
            characterFlowResult = createCharacterResponse(value)
        }

    var filmResponseType: ResponseStateRemote = ResponseStateRemote.DATA
        set(value) {
            field = value
            filmFlowResult = filmCharacterResponse(value)
        }

    private fun createCharacterResponse(responseState: ResponseStateRemote): Result<CharacterDto> {
        return when (responseState) {
            ResponseStateRemote.DATA -> Result.Success(DummyData.characterDto)
            ResponseStateRemote.EMPTY -> Result.Success(null)
            ResponseStateRemote.ERROR -> Result.Error(exception)
        }
    }

    private fun planetCharacterResponse(responseState: ResponseState): Flow<Result<PlanetDto>> {
        return when (responseState) {
            ResponseState.DATA -> flowOf(Result.Success(DummyData.planetDto))
            ResponseState.EMPTY -> flowOf(Result.Success(null))
            ResponseState.ERROR -> flowOf(Result.Error(exception))
        }
    }

    private fun filmCharacterResponse(responseState: ResponseStateRemote): Result<List<FilmDto>> {
        return when (responseState) {
            ResponseStateRemote.DATA -> Result.Success(listOf(DummyData.filmDto))
            ResponseStateRemote.EMPTY -> Result.Success(null)
            ResponseStateRemote.ERROR -> Result.Error(exception)
        }
    }

    override suspend fun getCharacter(characterUrl: String): Result<CharacterDto> {
        return characterFlowResult
    }

    override suspend fun getFilm(filmUrls: List<String>): Result<List<FilmDto>> {
        return filmFlowResult
    }

    override suspend fun getPlanet(planetUrl: String): Result<PlanetDto> {
        return planetFlowResult
    }
}

enum class ResponseStateRemote {
    DATA,
    ERROR,
    EMPTY
}


