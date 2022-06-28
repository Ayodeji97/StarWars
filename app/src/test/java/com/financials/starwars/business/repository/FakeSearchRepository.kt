package com.financials.starwars.business.repository

import com.financials.starwars.business.datasource.utils.DummyData
import com.financials.starwars.business.datasource.utils.TestConstants.TEST_ERROR
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepository
import com.financials.starwars.business.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf



class FakeSearchRepository : CharactersSearchRepository {

    val error_text = TEST_ERROR
    val exception = Exception(error_text)


    private var charactersSearchFlowResult : Flow<Result<List<Character>>> =
        flowOf(Result.Success(listOf(DummyData.character)))


    var responseType: ResponseState = ResponseState.DATA
        set(value) {
            field = value
            charactersSearchFlowResult = createResponse(value)
        }

    private fun createResponse (responseState: ResponseState) : Flow<Result<List<Character>>> {
        return when(responseState) {
            ResponseState.DATA -> flowOf(Result.Success(listOf(DummyData.character)))
            ResponseState.EMPTY -> flowOf(Result.Success(listOf()))
            ResponseState.ERROR -> flowOf(Result.Error(exception))
        }
    }

    override suspend fun charactersSearch(characterName: String): Flow<Result<List<Character>>> {
        return charactersSearchFlowResult
    }


}

enum class ResponseState {
    DATA,
    ERROR,
    EMPTY
}


