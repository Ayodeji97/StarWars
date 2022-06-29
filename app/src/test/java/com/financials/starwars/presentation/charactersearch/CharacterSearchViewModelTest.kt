package com.financials.starwars.presentation.charactersearch

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.financials.starwars.business.datasource.utils.DummyData
import com.financials.starwars.business.datasource.utils.MainCoroutineRule
import com.financials.starwars.business.domain.interactor.GetCharacterSearchUseCase
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepository
import com.financials.starwars.business.repository.fakes.FakeCharacterSearchUseCase
import com.financials.starwars.business.repository.fakes.FakeSearchRepository
import com.financials.starwars.business.utils.Result
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config


@RunWith(MockitoJUnitRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
class CharacterSearchViewModelTest {

    @Mock
    private lateinit var fakeCharactersSearchRepository: FakeSearchRepository

    @Mock
    private lateinit var fakeCharacterSearchUseCase: GetCharacterSearchUseCase

    @Mock
    private lateinit var characterSearchViewModel: CharacterSearchViewModel

    /**
     * This rules all related arch component background Job in the same thread
     * */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeCharactersSearchRepository = mock(FakeSearchRepository::class.java)
        fakeCharacterSearchUseCase = GetCharacterSearchUseCase(fakeCharactersSearchRepository)
        characterSearchViewModel = CharacterSearchViewModel(fakeCharacterSearchUseCase)
    }

    @Test
    fun `check that call to useCase return search result`() = runBlocking {
        `when`(fakeCharacterSearchUseCase.invoke("Angel")).thenReturn(
            flowOf(Result.Success(listOf(DummyData.character)))
        )

        characterSearchViewModel.onTriggeredEvent(CharacterSearchViewEvent.GetCharacterBySearch("Angel"))

        val listOfEmittedResult = mutableListOf<CharacterSearchViewState>()
        val job = launch {
            characterSearchViewModel.characterSearchViewState.toList(listOfEmittedResult)
        }

        assertThat(
            characterSearchViewModel.characterSearchViewState.value
                    is CharacterSearchViewState.Success
        ).isTrue()
        job.cancel()
    }

    @Test
    fun `check that call to useCase return null `() = runBlocking {
        `when`(fakeCharacterSearchUseCase.invoke("Angel")).thenReturn(
            flowOf(Result.Success(null))
        )

        characterSearchViewModel.onTriggeredEvent(CharacterSearchViewEvent.GetCharacterBySearch("Angel"))
        val listOfEmittedResult = mutableListOf<CharacterSearchViewState>()
        val job = launch {
            characterSearchViewModel.characterSearchViewState.toList(listOfEmittedResult)
        }

        assertThat(
            characterSearchViewModel.characterSearchViewState.value
                    is CharacterSearchViewState.Success
        ).isFalse()
        job.cancel()
    }
}