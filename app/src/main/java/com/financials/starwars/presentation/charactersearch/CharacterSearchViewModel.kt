package com.financials.starwars.presentation.charactersearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.datasource.remote.model.CharacterSearchDto
import com.financials.starwars.business.domain.interactor.GetCharacterSearchUseCase
import com.financials.starwars.business.domain.interactor.GetCharacterUseCase
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.utils.Constants.UNABLE_TO_FETCH_DATA
import com.financials.starwars.business.utils.Result
import com.financials.starwars.presentation.charactersearch.CharacterSearchViewEvent
import com.financials.starwars.presentation.charactersearch.CharacterSearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
    private val getCharacterSearchUseCase: GetCharacterSearchUseCase
) : ViewModel() {

    private var job: Job? = null

    private var _characterSearchViewState =
        MutableStateFlow<CharacterSearchViewState>(CharacterSearchViewState.IsLoading(false))
    val characterSearchViewState = _characterSearchViewState.asStateFlow()

    fun onTriggeredEvent(event: CharacterSearchViewEvent) {
        when (event) {
            is CharacterSearchViewEvent.GetCharacterBySearch -> {
                _characterSearchViewState.value = CharacterSearchViewState.IsLoading(true)
                getCharacterSearch(event.query)
            }
        }
    }

    private fun getCharacterSearch(characterName: String) {
        if (job != null) job?.cancel()
        job = viewModelScope.launch {
            getCharacterSearchUseCase.invoke(characterName).collect {
                when (it) {
                    is Result.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            _characterSearchViewState.value = CharacterSearchViewState.IsEmpty(true)
                        } else {
                            handleViewState(it.data, "")
                        }
                    }
                    is Result.Error -> {
                        handleViewState(
                            null,
                            it.exception.localizedMessage ?: UNABLE_TO_FETCH_DATA
                        )
                    }
                }
            }
        }
    }

    private fun handleViewState(charactersSearch: List<Character>?, error: String) {
        try {
            if (charactersSearch != null) {
                _characterSearchViewState.value = CharacterSearchViewState.Success(charactersSearch)
            } else {
                _characterSearchViewState.value =
                    CharacterSearchViewState.Error(UNABLE_TO_FETCH_DATA)
            }
        } catch (e: Exception) {
            _characterSearchViewState.value =
                CharacterSearchViewState.Error(e.localizedMessage ?: error)
        }
    }
}