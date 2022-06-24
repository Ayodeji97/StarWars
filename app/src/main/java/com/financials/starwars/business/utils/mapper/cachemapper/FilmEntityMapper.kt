package com.financials.starwars.business.utils.mapper.cachemapper

import com.financials.starwars.business.datasource.cache.model.FilmEntity
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.utils.mapper.base.BaseEntityMapper

class FilmEntityMapper : BaseEntityMapper<FilmEntity, Film> {
    override fun transformToDomain(type: FilmEntity): Film =
        Film(
            title = type.title,
            openingCrawl = type.openingCrawl
        )

    override fun transformToEntity(type: Film): FilmEntity =
        FilmEntity(
            title = type.title,
            openingCrawl = type.openingCrawl
        )
}