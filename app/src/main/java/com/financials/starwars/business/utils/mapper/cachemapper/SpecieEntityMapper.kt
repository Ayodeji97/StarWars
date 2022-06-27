package com.financials.starwars.business.utils.mapper.cachemapper

import com.financials.starwars.business.datasource.cache.model.SpecieEntity
import com.financials.starwars.business.domain.model.Specie
import com.financials.starwars.business.utils.mapper.base.BaseEntityMapper

class SpecieEntityMapper : BaseEntityMapper<SpecieEntity, Specie> {
    override fun transformToDomain(type: SpecieEntity): Specie =
        Specie(
            name = type.name,
            homeWorld = type.homeWorld,
            language = type.language
        )

    override fun transformToEntity(type: Specie): SpecieEntity =
        SpecieEntity(
            name = type.name,
            homeWorld = type.homeWorld,
            language = type.language
        )

}