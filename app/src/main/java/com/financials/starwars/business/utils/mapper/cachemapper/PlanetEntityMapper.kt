package com.financials.starwars.business.utils.mapper.cachemapper

import com.financials.starwars.business.datasource.cache.model.PlanetEntity
import com.financials.starwars.business.domain.model.Planet
import com.financials.starwars.business.utils.mapper.base.BaseEntityMapper

class PlanetEntityMapper : BaseEntityMapper<PlanetEntity, Planet> {
    override fun transformToDomain(type: PlanetEntity): Planet =
        Planet(
            name = type.name,
            population = type.population
        )

    override fun transformToEntity(type: Planet): PlanetEntity =
        PlanetEntity(
            name = type.name,
            population = type.population
        )
}