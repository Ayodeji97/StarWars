package com.financials.starwars.presentation.characterdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financials.starwars.business.domain.interactor.GetCharacterUseCase
import com.financials.starwars.business.domain.interactor.GetFilmUseCase
import com.financials.starwars.business.domain.interactor.GetPlanetUseCase
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.domain.model.Planet
import com.financials.starwars.business.utils.Constants
import com.financials.starwars.business.utils.Result
import com.financials.starwars.presentation.characterdetail.viewstate.CharacterDetailViewState
import com.financials.starwars.presentation.characterdetail.viewstate.FilmViewState
import com.financials.starwars.presentation.characterdetail.viewstate.PlanetViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getFilmUseCase: GetFilmUseCase,
    private val getPlanetUseCase: GetPlanetUseCase
) : ViewModel() {

    private var _characterSearchViewState =
        MutableStateFlow<CharacterDetailViewState>(CharacterDetailViewState.IsLoading(false))

    val characterSearchViewState = _characterSearchViewState.asStateFlow()

    private var _filmViewState = MutableStateFlow<FilmViewState>(FilmViewState.IsLoading(false))
    val filmViewState = _filmViewState.asStateFlow()

    private var _planetViewState = MutableStateFlow<PlanetViewState>(PlanetViewState.IsLoading(false))
    val planetViewState = _planetViewState.asStateFlow()


    fun onTriggeredEvent(event: CharacterDetailViewEvent) {
        when (event) {
            is CharacterDetailViewEvent.GetCharacter -> {
                getCharacter(event.characterUrl)
            }
            is CharacterDetailViewEvent.GetPlanet -> {
                getPlanet(event.planetUrl)
            }
            is CharacterDetailViewEvent.GetFilm -> getFilm(event.filmUrl)

        }
    }


    private fun getCharacter(characterUrl: String) {
        viewModelScope.launch {
            getCharacterUseCase.invoke(characterUrl).collect {
                when (it) {
                    is Result.Loading -> {
                        handleCharacterViewState(null, "", true)
                    }
                    is Result.Success -> {
                        handleCharacterViewState(it.data, "", false)
                    }
                    is Result.Error -> {
                        handleCharacterViewState(
                            null,
                            it.exception.localizedMessage ?: Constants.UNABLE_TO_FETCH_DATA, false
                        )
                    }

                }
            }
        }
    }

    private fun getPlanet (planetUrl : String) {
        viewModelScope.launch {
            getPlanetUseCase.invoke(planetUrl).collectLatest {
                when(it) {
                    is Result.Loading -> {
                        handlePlanetState(null, "", true)
                    }
                    is Result.Success -> {
                        handlePlanetState(it.data, "", false)
                    }
                    is Result.Error -> {
                        handlePlanetState(
                            null,
                            it.exception.localizedMessage ?: Constants.UNABLE_TO_FETCH_DATA, false
                        )
                    }
                }
            }
        }
    }

    private fun getFilm (filmUrl : List<String>) {
        viewModelScope.launch {
            getFilmUseCase.invoke(filmUrl).collectLatest {
                when (it) {
                    is Result.Loading -> {
                        handleFilmState(null, "", true)
                    }
                    is Result.Success -> {
                        handleFilmState(it.data, "", false)
                    }
                    is Result.Error -> {
                        handleFilmState(
                            null,
                            it.exception.localizedMessage ?: Constants.UNABLE_TO_FETCH_DATA, false
                        )
                    }
                }
            }
        }
    }

    private fun handleCharacterViewState(
        characterDetail: CharacterDetail?,
        error: String,
        isLoading: Boolean
    ) {
        _characterSearchViewState.value = CharacterDetailViewState.IsLoading(isLoading)
        try {
            if (characterDetail != null) {
                _characterSearchViewState.value = CharacterDetailViewState.Success(characterDetail)
            } else {
                _characterSearchViewState.value = CharacterDetailViewState.Success(null)
            }
        } catch (e: Exception) {
            _characterSearchViewState.value =
                CharacterDetailViewState.Error(e.localizedMessage ?: error)
        }
    }

    private fun handleFilmState (
        film: List<Film>?,
        error: String,
        isLoading: Boolean
    ) {
        _filmViewState.value = FilmViewState.IsLoading(isLoading)
        try {
            Log.i("Films", "Anyone $film")
            if (film != null) {
                _filmViewState.value = FilmViewState.Success(film)
            } else {
                _filmViewState.value = FilmViewState.Success(null)
            }
        } catch (e: Exception) {
            _filmViewState.value =
                FilmViewState.Error(e.localizedMessage ?: error)
        }
    }

    fun handlePlanetState (planet: Planet?,  error: String,
                           isLoading: Boolean) {
        _planetViewState.value = PlanetViewState.IsLoading(isLoading)
        try {
            if (planet != null) {
                _planetViewState.value = PlanetViewState.Success(planet)
            } else {
                _planetViewState.value = PlanetViewState.Success(null)
            }
        } catch (e: Exception) {
            _planetViewState.value =
                PlanetViewState.Error(e.localizedMessage ?: error)
        }
    }


}