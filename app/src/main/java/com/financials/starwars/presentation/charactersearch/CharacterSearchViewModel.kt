package com.financials.starwars.presentation.charactersearch

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

    private var _characterSearchViewState =
        MutableStateFlow<CharacterSearchViewState>(CharacterSearchViewState.IsLoading(false))


    val characterSearchViewState = _characterSearchViewState.asStateFlow()


    fun onTriggeredEvent (event: CharacterSearchViewEvent) {
        when (event) {
            is CharacterSearchViewEvent.GetCharacterBySearch -> {
                getCharacterSearch(event.query)
            }
        }
    }


    private fun getCharacterSearch (characterName : String) {
        viewModelScope.launch {
            getCharacterSearchUseCase.invoke(characterName).collect {
                when(it) {
                    is Result.Loading -> {
                        handleViewState(null, "", true)
                    }
                    is Result.Success -> {
                        handleViewState(it.data, "", false)
                    }
                    is Result.Error -> {
                        handleViewState(null,
                            it.exception.localizedMessage ?: UNABLE_TO_FETCH_DATA, false)
                    }
                }
            }
        }
    }



    private fun handleViewState (charactersSearch: List<Character>?, error : String, isLoading: Boolean) {
        _characterSearchViewState.value = CharacterSearchViewState.IsLoading(isLoading)
        try {
            if (charactersSearch != null) {
                _characterSearchViewState.value = CharacterSearchViewState.Success(charactersSearch)
            } else {
                _characterSearchViewState.value = CharacterSearchViewState.Success(null)
            }
        } catch (e : Exception){
            _characterSearchViewState.value =
                CharacterSearchViewState.Error(e.localizedMessage ?: error)
        }
    }
}