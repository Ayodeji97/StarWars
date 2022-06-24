package com.financials.starwars.business.utils.mapper.base

interface BaseEntityMapper<T, D> {

    fun transformToDomain(type: T): D

    fun transformToEntity(type: D): T
}