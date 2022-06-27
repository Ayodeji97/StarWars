package com.financials.starwars.presentation.adapter.filmadapter

import androidx.recyclerview.widget.RecyclerView
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.databinding.FilmListItemBinding

class FilmViewHolder(
    private val ui: FilmListItemBinding,
) : RecyclerView.ViewHolder(ui.root) {

    fun bind(film: Film) {
        ui.apply {
            fragmentFilmTitleTv.text = film.title
            fragmentFilmDescriptionTv.text = film.openingCrawl
        }
    }

}