package com.financials.starwars.presentation.adapter.filmadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.databinding.FilmListItemBinding

class FilmAdapter : ListAdapter<Film, FilmViewHolder>(FilmComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val ui = FilmListItemBinding.inflate(layoutInflater, parent, false)
        return FilmViewHolder(ui)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val currentBinding = getItem(position)
        currentBinding.let {
            holder.bind(it)
        }
    }
}