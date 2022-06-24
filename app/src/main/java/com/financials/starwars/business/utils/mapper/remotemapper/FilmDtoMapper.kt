package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper

class FilmDtoMapper : BaseDtoMapper<FilmDto, Film> {
    override fun transformToDomain(type: FilmDto): Film =
        Film(
            title = type.title,
            openingCrawl = type.openingCrawl
        )

    override fun transformToDto(type: Film): FilmDto =
        FilmDto(
            title = type.title,
            openingCrawl = type.openingCrawl
        )
}