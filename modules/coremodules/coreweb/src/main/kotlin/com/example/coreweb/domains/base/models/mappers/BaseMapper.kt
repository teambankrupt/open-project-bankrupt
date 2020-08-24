package com.example.coreweb.domains.base.models.mappers

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.example.coreweb.domains.base.entities.BaseEntity

interface BaseMapper<T : BaseEntity, S : BaseDto> {
    fun map(entity: T): S
    fun map(dto: S, exEntity: T?): T
}