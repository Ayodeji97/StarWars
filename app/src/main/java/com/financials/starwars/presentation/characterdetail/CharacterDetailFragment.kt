package com.financials.starwars.presentation.characterdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
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

    private var currentBinding : FragmentCharacterDetailBinding? = null
    private val ui get() = currentBinding!!
    private lateinit var character : Character

    private val characterDetailViewModel : CharacterDetailViewModel by viewModels()

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
            val action = CharacterDetailFragmentDirections.actionCharacterDetailFragmentToCharacterSearchFragment()
            findNavController().navigate(action)
        }

        filmAdapter = FilmAdapter()
        initRecyclerView()
        updateBasicProfileView ()
        getCharacter(characterUrl = character.url)
        subscribeGetCharacterObserver ()
        subscribeGetPlanetObserver ()


        subscribeGetFilmObserver ()

    }

    private fun updateBasicProfileView () {
        val heightInInch = heightConverterToInch(character.height.toInt())
        ui.apply {
            basicProfileNameTv.text = getString(R.string.profile_name_str, character.name)
            basicProfileBirthYearTv.text = getString(R.string.profile_birth_year_str, character.birthYear)
            basicProfileHeightTv.text = getString(R.string.profile_height_str, character.height, heightInInch)
        }
    }

    private fun initRecyclerView() {
        ui.apply {
            recyclerViewCharacterSearch.apply {
                adapter = filmAdapter
                layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }
    }



    private fun getCharacter (characterUrl : String) {
        characterDetailViewModel.onTriggeredEvent(CharacterDetailViewEvent.GetCharacter(
            characterUrl
        ))
    }

    private fun subscribeGetCharacterObserver () {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            characterDetailViewModel.characterSearchViewState.collect {
                when(it) {
                    is CharacterDetailViewState.IsLoading -> {

                    }
                    is CharacterDetailViewState.Success -> {
                        getPlanet(it.character?.planet ?: "")
                        Log.i("Films", "NothingWrong ${it.character?.films}")
                        getFilms(it.character?.films ?: listOf("123", "345"))
                    }
                    is CharacterDetailViewState.Error -> {

                    }
                }
            }
        }
    }

    private fun getPlanet (planetUrl : String) {
        characterDetailViewModel.onTriggeredEvent(CharacterDetailViewEvent.GetPlanet(planetUrl))
    }

    private fun subscribeGetPlanetObserver () {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            characterDetailViewModel.planetViewState.collectLatest {
                when(it) {
                    is PlanetViewState.IsLoading -> {

                    }
                    is PlanetViewState.Success -> {
                        val planetName = it.planet?.name
                        val population = it.planet?.population
                        ui.apply {
                            planetNameTv.text = getString(R.string.planet_name_str, planetName)
                            planetPopulationTv.text = getString(R.string.planet_population_str, population)
                        }

                    }
                    is PlanetViewState.Error -> {
                        Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun getFilms (filmUrls: List<String>) {
        Log.i("Films", "${filmUrls}")
        characterDetailViewModel
            .onTriggeredEvent(CharacterDetailViewEvent.GetFilm(filmUrls))
    }

    private fun subscribeGetFilmObserver () {
        viewLifecycleOwner.lifecycleScope.launch {
            characterDetailViewModel.filmViewState.collect {
                when(it) {
                    is FilmViewState.IsLoading -> {

                    }
                    is FilmViewState.Success -> {
                        Log.i("Films", "Pleassee${it.film}")
                        initRecyclerView()
                        filmAdapter.submitList(it.film)

                    }
                    is FilmViewState.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
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