package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.remote.model.PlanetDto
import com.financials.starwars.business.domain.model.Planet
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper
import javax.inject.Inject

class PlanetDtoMapper @Inject constructor() : BaseDtoMapper<PlanetDto, Planet> {
    override fun transformToDomain(type: PlanetDto): Planet =
        Planet(
            name = type.name,
            population = type.population
        )

}