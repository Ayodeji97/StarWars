package com.financials.starwars.presentation.adapter.characteradapter

import androidx.recyclerview.widget.RecyclerView
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.databinding.CharactersListItemBinding

class CharactersSearchViewHolder(
    private val ui: CharactersListItemBinding,
    private val onCharacterClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(ui.root) {
    fun bind(character: Character) {
        ui.apply {
            fragmentCharacterListItemTv.text = character.name
        }
    }

    init {
        ui.apply {
            root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCharacterClicked(position)
                }
            }
        }
    }
}