package com.financials.starwars.business.utils.mapper.base

interface BaseDtoMapper<T, D> {

    fun transformToDomain(type: T): D

    fun transformToDto(type: D): T
}