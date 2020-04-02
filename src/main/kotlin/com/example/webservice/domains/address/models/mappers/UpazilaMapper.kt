package com.example.webservice.domains.address.models.mappers

import com.example.webservice.domains.address.models.dto.UpazilaDto
import com.example.webservice.domains.address.models.entities.Upazila
import com.example.webservice.domains.address.services.DistrictService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpazilaMapper(@Autowired val districtService: DistrictService) {

    fun map(entity: Upazila): UpazilaDto {
        val dto = UpazilaDto()
        dto.id = entity.id
        dto.nameEn = entity.nameEn
        dto.nameBn = entity.nameBn
        dto.created = entity.createdAt
        dto.updatedAt = entity.updatedAt
        dto.districtId = entity.district?.id
        return dto
    }

    fun map(dto: UpazilaDto, upazila: Upazila?): Upazila {
        var entity = upazila
        if (entity == null)entity = Upazila()
        entity.nameEn = dto.nameEn
        entity.nameBn = dto.nameBn
        entity.district = districtService.find(dto.districtId!!).orElseThrow { NotFoundException() }
        return entity
    }


}
