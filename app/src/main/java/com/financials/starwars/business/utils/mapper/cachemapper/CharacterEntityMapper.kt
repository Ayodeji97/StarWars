package com.financials.starwars.business.utils.mapper.cachemapper

import com.financials.starwars.business.datasource.cache.model.CharacterEntity
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.utils.mapper.base.BaseEntityMapper

class CharacterEntityMapper : BaseEntityMapper<CharacterEntity, Character> {
    override fun transformToDomain(type: CharacterEntity): Character =
        Character(
            name = type.name,
            birthYear = type.birthYear,
            height = type.height,
            url = type.url
        )

    override fun transformToEntity(type: Character): CharacterEntity =
        CharacterEntity(
            name = type.name,
            birthYear = type.birthYear,
            height = type.height,
            url = type.url
        )
}