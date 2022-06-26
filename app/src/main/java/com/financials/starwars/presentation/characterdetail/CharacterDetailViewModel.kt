package com.financials.starwars.presentation.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.domain.interactor.GetCharacterUseCase
import com.financials.starwars.business.utils.Constants
import com.financials.starwars.business.utils.Result
import com.financials.starwars.presentation.charactersearch.CharacterSearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
        ): ViewModel() {

    private var _characterSearchViewState =
        MutableStateFlow<CharacterSearchViewState>(CharacterSearchViewState.IsLoading(false))

    val characterSearchViewState = _characterSearchViewState.asStateFlow()

//    private fun handleViewState (characterDto: CharacterDto?, error : String, isLoading: Boolean) {
//        _characterSearchViewState.value = CharacterSearchViewState.IsLoading(isLoading)
//        try {
//            if (characterDto != null) {
//                _characterSearchViewState.value = CharacterSearchViewState.Success(characterDto)
//            } else {
//                _characterSearchViewState.value = CharacterSearchViewState.Success(null)
//            }
//        } catch (e : Exception){
//            _characterSearchViewState.value =
//                CharacterSearchViewState.Error(e.localizedMessage ?: error)
//        }
//    }

//    private fun getCharacter (characterUrl : String) {
//        viewModelScope.launch {
//            getCharacterUseCase.invoke(characterUrl).collect {
//                when(it) {
//                    Result.Loading -> {
//                        handleViewState(null, "", true)
//                    }
//                    is Result.Success -> {
//                        handleViewState(it.data, "", false)
//                    }
//                    is Result.Error -> {
//                        handleViewState(null,
//                            it.exception.localizedMessage ?: Constants.UNABLE_TO_FETCH_DATA, false)
//                    }
//
//                }
//            }
//        }
//    }
}