package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.remote.model.SpecieDto
import com.financials.starwars.business.domain.model.Specie
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper

class SpecieDtoMapper : BaseDtoMapper<SpecieDto, Specie> {
    override fun transformToDomain(type: SpecieDto): Specie =
        Specie(
            name = type.name,
            homeWorld = type.homeWorld,
            language = type.language
        )

    override fun transformToDto(type: Specie): SpecieDto =
        SpecieDto(
            name = type.name,
            homeWorld = type.homeWorld,
            language = type.language
        )
}