package com.financials.starwars.business.utils.mapper.remotemapper

import com.financials.starwars.business.datasource.cache.model.CharacterDetailEntity
import com.financials.starwars.business.datasource.remote.model.CharacterDto
import com.financials.starwars.business.domain.model.Character
import com.financials.starwars.business.utils.mapper.base.BaseDtoMapper
import javax.inject.Inject

class CharacterDtoMapper @Inject constructor() : BaseDtoMapper<List<CharacterDto>, List<Character>> {
    override fun transformToDomain(type: List<CharacterDto>): List<Character> =
        type.map { characterDto ->
            Character(
                name = characterDto.name,
                birthYear = characterDto.birthYear,
                height = characterDto.height,
                url = characterDto.url
            )
        }
}