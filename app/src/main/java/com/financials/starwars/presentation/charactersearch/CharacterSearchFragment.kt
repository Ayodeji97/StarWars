package com.financials.starwars.presentation.charactersearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.financials.starwars.R
import com.financials.starwars.databinding.FragmentCharactersSearchBinding
import com.financials.starwars.presentation.adapter.characteradapter.CharacterSearchAdapter
import com.financials.starwars.presentation.utils.afterTextChangedDelayed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharacterSearchFragment : Fragment() {

    private var currentBinding: FragmentCharactersSearchBinding? = null
    private val ui get() = currentBinding!!

    private val characterSearchViewModel: CharacterSearchViewModel by viewModels()
    private lateinit var characterSearchAdapter: CharacterSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentCharactersSearchBinding.inflate(inflater)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        userSearchAction()
        subscribeObserver()

        if (characterSearchAdapter.currentList.isEmpty()) {
            ui.apply {
                fragmentCharacterSearchErrorTv.visibility = View.VISIBLE
                fragmentCharacterSearchErrorTv.text = getText(R.string.empty_list_str)
            }
        }
    }

    private fun initRecyclerView() {
        characterSearchAdapter = CharacterSearchAdapter(
            onCharacterClicked = { character ->
                val action =
                    CharacterSearchFragmentDirections.actionCharacterSearchFragmentToCharacterDetailFragment(
                        character
                    )
                findNavController().navigate(action)
            }
        )

        ui.apply {
            recyclerViewCharacterSearch.apply {
                adapter = characterSearchAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            characterSearchViewModel.characterSearchViewState.collectLatest {
                when (it) {
                    is CharacterSearchViewState.IsEmpty -> {
                        ui.apply {
                            progressBar.isVisible = false
                            recyclerViewCharacterSearch.isVisible = false
                            fragmentCharacterSearchErrorTv.visibility = View.VISIBLE
                            fragmentCharacterSearchErrorTv.text =
                                getText(R.string.search_not_found_str)
                        }
                    }
                    is CharacterSearchViewState.IsLoading -> {
                        ui.apply {
                            fragmentCharacterSearchErrorTv.visibility = View.INVISIBLE
                            recyclerViewCharacterSearch.isVisible = false
                            ui.progressBar.isVisible = it.isLoading
                        }
                    }
                    is CharacterSearchViewState.Success -> {
                        ui.apply {
                            recyclerViewCharacterSearch.isVisible = true
                            fragmentCharacterSearchErrorTv.visibility = View.INVISIBLE
                            ui.progressBar.isVisible = false
                        }
                        characterSearchAdapter.submitList(it.charactersSearch)
                    }

                    is CharacterSearchViewState.Error -> {
                        ui.apply {
                            progressBar.isVisible = false
                            recyclerViewCharacterSearch.isVisible = false
                            fragmentCharacterSearchErrorTv.visibility = View.VISIBLE
                            fragmentCharacterSearchErrorTv.text = it.error
                        }
                    }

                }
            }
        }
    }

    private fun getCharacterSearch() {
        val userSearch = ui.fragmentCharacterSearchEt.text.toString()
        characterSearchViewModel.onTriggeredEvent(
            CharacterSearchViewEvent.GetCharacterBySearch(
                userSearch
            )
        )
    }

    private fun userSearchAction() {
        ui.fragmentCharacterSearchEt.afterTextChangedDelayed {
            ui.apply {
                fragmentCharacterSearchErrorTv.visibility = View.INVISIBLE
            }
            if (it.isNotBlank()) {
                getCharacterSearch()
            } else {
                characterSearchAdapter.submitList(emptyList())
                ui.apply {
                    fragmentCharacterSearchErrorTv.visibility = View.VISIBLE
                    fragmentCharacterSearchErrorTv.text = getText(R.string.empty_list_str)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }
}