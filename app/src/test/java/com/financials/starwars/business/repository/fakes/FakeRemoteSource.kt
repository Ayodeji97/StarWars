package com.financials.starwars.business.repository.fakes

import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.datasource.remote.remotesource.charactersearch.CharacterSearchRemoteSource
import com.financials.starwars.business.datasource.utils.DummyData
import com.financials.starwars.business.datasource.utils.TestConstants
import com.financials.starwars.business.utils.Result

class FakeRemoteSource  : CharacterSearchRemoteSource {

    val error_text = TestConstants.TEST_ERROR
    val exception = Exception(error_text)


    private var charactersSearchResult : Result<CharacterSearchDto> =
        Result.Success(DummyData.characterSearchDto)


    var responseType: ResponseRemoteState = ResponseRemoteState.DATA
        set(value) {
            field = value
            charactersSearchResult = createResponse(value)
        }

    private fun createResponse (responseState: ResponseRemoteState) : Result<CharacterSearchDto> {
        return when(responseState) {
            ResponseRemoteState.DATA -> Result.Success(DummyData.characterSearchDto)
            ResponseRemoteState.ERROR -> Result.Error(exception)
            ResponseRemoteState.EMPTY -> Result.Success(null)
        }
    }

    override suspend fun charactersSearch(characterName: String): Result<CharacterSearchDto> {
        return charactersSearchResult
    }
}

enum class ResponseRemoteState {
    DATA,
    ERROR,
    EMPTY
}

