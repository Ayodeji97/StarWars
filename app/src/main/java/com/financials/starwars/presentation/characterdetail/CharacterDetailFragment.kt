package com.financials.starwars.presentation.characterdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.financials.starwars.R
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.databinding.FragmentCharacterDetailBinding
import com.financials.starwars.presentation.adapter.characteradapter.CharacterSearchAdapter
import com.financials.starwars.presentation.adapter.filmadapter.FilmAdapter
import com.financials.starwars.presentation.characterdetail.viewstate.CharacterDetailViewState
import com.financials.starwars.presentation.characterdetail.viewstate.FilmViewState
import com.financials.starwars.presentation.characterdetail.viewstate.PlanetViewState
import com.financials.starwars.presentation.charactersearch.CharacterSearchFragmentDirections
import com.financials.starwars.presentation.utils.heightConverterToInch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var currentBinding: FragmentCharacterDetailBinding? = null
    private val ui get() = currentBinding!!
    private lateinit var character: Character

    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    private lateinit var filmAdapter: FilmAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentCharacterDetailBinding.inflate(inflater)
        return ui.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        character = CharacterDetailFragmentArgs.fromBundle(requireArguments()).character

        ui.fragmentDetailBackArrow.setOnClickListener {
            findNavController().popBackStack(R.id.characterSearchFragment, false)
        }

        filmAdapter = FilmAdapter()
        initRecyclerView()
        updateBasicProfileView()
        getCharacter(characterUrl = character.url)
        subscribeGetCharacterObserver()
        subscribeGetPlanetObserver()
        subscribeGetFilmObserver()

    }

    private fun updateBasicProfileView() {
        val heightInInch = heightConverterToInch(character.height.toInt())
        ui.apply {
            basicProfileNameTv.text = getString(R.string.profile_name_str, character.name)
            basicProfileBirthYearTv.text =
                getString(R.string.profile_birth_year_str, character.birthYear)
            basicProfileHeightTv.text =
                getString(R.string.profile_height_str, character.height, heightInInch)
        }
    }

    private fun initRecyclerView() {
        ui.apply {
            recyclerViewCharacterSearch.apply {
                adapter = filmAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL, false
                )
                setHasFixedSize(true)
            }
        }
    }


    private fun getCharacter(characterUrl: String) {
        characterDetailViewModel.onTriggeredEvent(
            CharacterDetailViewEvent.GetCharacter(
                characterUrl
            )
        )
    }

    private fun subscribeGetCharacterObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            characterDetailViewModel.characterSearchViewState.collect {
                when (it) {
                    is CharacterDetailViewState.IsLoading -> {
                        ui.apply {
                            planetProgressBar.isVisible = it.isLoading
                        }

                    }
                    is CharacterDetailViewState.Success -> {
                        ui.planetProgressBar.isVisible = false
                        getPlanet(it.character?.planet ?: "")
                        getFilms(it.character?.films ?: emptyList())
                    }
                    is CharacterDetailViewState.Error -> {
                        ui.apply {
                            progressBar.isVisible = false
                            fragmentDetailPlanetErrorTv.isVisible = true
                            fragmentDetailPlanetErrorTv.text = it.error
                        }
                    }
                }
            }
        }
    }

    private fun getPlanet(planetUrl: String) {
        characterDetailViewModel.onTriggeredEvent(CharacterDetailViewEvent.GetPlanet(planetUrl))
    }

    private fun subscribeGetPlanetObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            characterDetailViewModel.planetViewState.collectLatest {
                when (it) {
                    is PlanetViewState.IsLoading -> {
                        ui.apply {
                            planetProgressBar.isVisible = it.isLoading
                        }
                    }
                    is PlanetViewState.Success -> {
                        val planetName = it.planet?.name
                        val population = it.planet?.population
                        ui.apply {
                            planetNameTv.text = getString(R.string.planet_name_str, planetName)
                            planetPopulationTv.text =
                                getString(R.string.planet_population_str, population)
                            planetProgressBar.isVisible = false
                        }

                    }
                    is PlanetViewState.Error -> {
                        ui.apply {
                            progressBar.isVisible = false
                            fragmentDetailPlanetErrorTv.isVisible = true
                            fragmentDetailPlanetErrorTv.text = it.error
                        }
                    }
                }
            }
        }
    }


    private fun getFilms(filmUrls: List<String>) {
        characterDetailViewModel
            .onTriggeredEvent(CharacterDetailViewEvent.GetFilm(filmUrls))
    }

    private fun subscribeGetFilmObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            characterDetailViewModel.filmViewState.collect {
                when (it) {
                    is FilmViewState.IsEmpty -> {
                        ui.apply {
                            progressBar.isVisible = false
                            recyclerViewCharacterSearch.isVisible = false
                            fragmentDetailFilmErrorTv.visibility = View.VISIBLE
                            fragmentDetailFilmErrorTv.text =
                                getText(R.string.search_not_found_films_str)
                        }
                    }
                    is FilmViewState.IsLoading -> {
                        ui.apply {
                            fragmentDetailFilmErrorTv.isVisible = false
                            recyclerViewCharacterSearch.isVisible = false
                            ui.progressBar.isVisible = it.isLoading
                        }
                    }
                    is FilmViewState.Success -> {
                        initRecyclerView()
                        ui.progressBar.isVisible = false
                        ui.recyclerViewCharacterSearch.isVisible = true
                        filmAdapter.submitList(it.film)

                    }
                    is FilmViewState.Error -> {
                        ui.apply {
                            progressBar.isVisible = false
                            recyclerViewCharacterSearch.isVisible = false
                            fragmentDetailFilmErrorTv.isVisible = true
                            fragmentDetailPlanetErrorTv.text = it.error
                        }
                    }

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }
}