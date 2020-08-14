package com.example.coreweb.domains.address.models.mappers

import com.example.coreweb.domains.address.models.dto.DistrictDto
import com.example.coreweb.domains.address.models.entities.District
import com.example.coreweb.domains.address.services.DivisionService
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DistrictMapper(@Autowired val divisionService: DivisionService) {

    fun map(district: District): DistrictDto {
        val dto = DistrictDto();
        dto.id = district.id
        dto.nameEn = district.nameEn
        dto.nameBn = district.nameBn
        dto.divisionId = district.division?.id
        dto.createdAt = district.createdAt
        dto.updatedAt = district.updatedAt
        return dto
    }

    fun map(dto: DistrictDto, district: District?): District {
        var d = district
        if (d == null) d = District()
        d.nameEn = dto.nameEn
        d.nameBn = dto.nameBn
        d.division = dto.divisionId?.let { this.divisionService.find(it).orElseThrow { NotFoundException() } }
        return d
    }

}
