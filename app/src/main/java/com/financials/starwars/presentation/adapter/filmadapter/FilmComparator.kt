package com.financials.starwars.presentation.adapter.filmadapter

import androidx.recyclerview.widget.DiffUtil
import com.financials.starwars.business.domain.model.Film

class FilmComparator : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem == newItem
}