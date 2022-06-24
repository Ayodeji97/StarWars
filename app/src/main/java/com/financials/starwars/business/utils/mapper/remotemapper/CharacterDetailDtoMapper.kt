package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.remote.model.CharacterDetailDto
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper

class CharacterDetailDtoMapper : BaseDtoMapper<CharacterDetailDto, CharacterDetail> {
    override fun transformToDomain(type: CharacterDetailDto): CharacterDetail =
        CharacterDetail(
            films = type.films,
            planet = type.planet,
            species = type.species,
            url = type.url
        )

    override fun transformToDto(type: CharacterDetail): CharacterDetailDto =
        CharacterDetailDto(
            films = type.films,
            planet = type.planet,
            species = type.species,
            url = type.url
        )
}