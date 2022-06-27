package com.financials.starwars.business.utils.mapper.cachemapper

import com.financials.starwars.business.datasource.cache.model.CharacterDetailEntity
import com.financials.starwars.business.domain.model.CharacterDetail
import com.financials.starwars.business.utils.mapper.base.BaseEntityMapper

class CharacterDetailsEntityMapper : BaseEntityMapper<CharacterDetailEntity, CharacterDetail> {
    override fun transformToDomain(type: CharacterDetailEntity): CharacterDetail =
        CharacterDetail(
            films = type.films,
            planet = type.planet,
            species = type.species,
            url = type.url
        )

    override fun transformToEntity(type: CharacterDetail): CharacterDetailEntity =
        CharacterDetailEntity(
            films = type.films,
            planet = type.planet,
            species = type.species,
            url = type.url
        )
}