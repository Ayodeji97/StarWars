package com.financials.starwars.presentation.adapter.characteradapter

import androidx.recyclerview.widget.DiffUtil
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.domain.model.Character

class CharactersSearchComparator : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}