package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper

class CharacterDtoMapper : BaseDtoMapper<CharacterDto, Character> {
    override fun transformToDomain(type: CharacterDto): Character =
        Character(
            name = type.name,
            birthYear = type.birthYear,
            height = type.height,
            url = type.url
        )

    override fun transformToDto(type: Character): CharacterDto =
        CharacterDto(
            name = type.name,
            birthYear = type.birthYear,
            height = type.height,
            url = type.url
        )
}