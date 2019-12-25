package com.example.webservice.domains.common.models.mappers

import com.example.webservice.domains.common.models.dtos.BaseDto
import com.example.webservice.domains.common.models.entities.base.BaseEntity

interface BaseMapper<T : BaseEntity, S : BaseDto> {
    fun map(entity: T): S
    fun map(dto: S, exEntity: T?): T
}