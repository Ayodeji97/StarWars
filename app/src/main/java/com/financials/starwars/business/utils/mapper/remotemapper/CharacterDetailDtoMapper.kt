package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.cache.model.CharacterDetailEntity
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper

class CharacterDetailDtoMapper : BaseDtoMapper<CharacterDto, CharacterDetail> {
    override fun transformToDomain(type: CharacterDto): CharacterDetail =
        CharacterDetail(
            films = type.films,
            planet = type.homeWorld,
            species = type.species,
            url = type.url
        )
}