package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.remote.model.FilmDto
import com.financials.starwars.business.domain.model.Film
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper
import javax.inject.Inject

class FilmDtoMapper @Inject constructor() : BaseDtoMapper<List<FilmDto>, List<Film>> {

    override fun transformToDomain(type: List<FilmDto>): List<Film> =
        type.map { filmDto->
            Film(
                title = filmDto.title,
                openingCrawl = filmDto.openingCrawl
            )
        }

}