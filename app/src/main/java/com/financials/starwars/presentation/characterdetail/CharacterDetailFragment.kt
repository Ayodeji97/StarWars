package com.financials.starwars.presentation.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.financials.starwars.databinding.FragmentCharacterDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var currentBinding : FragmentCharacterDetailBinding? = null
    private val ui get() = currentBinding!!

    private lateinit var characterUrl : String

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
        characterUrl = CharacterDetailFragmentArgs.fromBundle(requireArguments()).characterUrl

        Toast.makeText(requireContext(), "$characterUrl", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }
}