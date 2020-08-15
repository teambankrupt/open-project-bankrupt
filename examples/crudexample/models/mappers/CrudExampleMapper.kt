package com.example.app.domains.crudexample.models.mappers

import com.example.app.domains.crudexample.models.dtos.CrudExampleDto
import com.example.app.domains.crudexample.models.entities.CrudExample
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.stereotype.Component

@Component
class CrudExampleMapper : BaseMapper<CrudExample, CrudExampleDto> {

    override fun map(entity: CrudExample): CrudExampleDto {
        val dto = CrudExampleDto()
        dto.id = entity.id
        dto.createdAt = entity.createdAt
        dto.updatedAt = entity.updatedAt

        return dto
    }

    override fun map(dto: CrudExampleDto, exEntity: CrudExample?): CrudExample {
        val entity = exEntity ?: CrudExample()

        return entity
    }
}