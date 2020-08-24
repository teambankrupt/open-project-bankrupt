package com.example.coreweb.domains.address.models.mappers

import com.example.coreweb.domains.address.models.dto.DivisionDto
import com.example.coreweb.domains.address.models.entities.Division
import org.springframework.stereotype.Component

@Component
class DivisionMapper {

    fun map(division: Division): DivisionDto {
        val dto = DivisionDto()
        dto.id = division.id
        dto.nameEn = division.nameEn
        dto.nameBn = division.nameBn
        dto.createdAt = division.createdAt
        dto.updatedAt = division.updatedAt
        return dto
    }

    fun map(dto: DivisionDto, division: Division?): Division {
        var d = division
        if (d == null) d  = Division()

        d.nameEn = dto.nameEn
        d.nameBn = dto.nameBn
        return d
    }

}
