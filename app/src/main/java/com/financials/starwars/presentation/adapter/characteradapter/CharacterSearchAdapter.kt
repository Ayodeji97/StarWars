package com.financials.starwars.presentation.adapter.characteradapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.financials.starwars.business.domain.model.Character

import com.financials.starwars.databinding.CharactersListItemBinding

class CharacterSearchAdapter(
    private val onCharacterClicked: (Character) -> Unit
) :
    ListAdapter<Character, CharactersSearchViewHolder>(CharactersSearchComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersSearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val ui = CharactersListItemBinding.inflate(layoutInflater, parent, false)
        return CharactersSearchViewHolder(
            ui,
            onCharacterClicked = { position ->
                val character = getItem(position)
                if (character != null) {
                    onCharacterClicked(character)
                }
            }
        )
    }

    override fun onBindViewHolder(holder: CharactersSearchViewHolder, position: Int) {
        val currentBinding = getItem(position)
        currentBinding.let {
            holder.bind(it)
        }
    }


}