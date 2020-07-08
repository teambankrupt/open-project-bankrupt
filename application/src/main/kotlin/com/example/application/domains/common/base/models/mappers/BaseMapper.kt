package com.example.application.domains.common.base.models.mappers

import com.example.application.domains.common.base.models.dtos.BaseDto
import com.example.application.domains.common.base.models.entities.BaseEntity

interface BaseMapper<T : BaseEntity, S : BaseDto> {
    fun map(entity: T): S
    fun map(dto: S, exEntity: T?): T
}