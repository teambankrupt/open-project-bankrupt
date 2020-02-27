package com.example.webservice.domains.address.models.mappers

import com.example.webservice.domains.address.models.dto.VillageDto
import com.example.webservice.domains.address.models.entities.Village
import com.example.webservice.domains.address.services.UnionService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VillageMapper(@Autowired val unionService: UnionService) {

    fun map(village: Village): VillageDto {
        val dto = VillageDto()
        dto.id = village.id
        dto.nameEn = village.nameEn
        dto.nameBn = village.nameBn
        dto.unionId = village.union?.id
        dto.created = village.createdAt
        dto.updatedAt = village.updatedAt
        return dto
    }

    fun map(dto: VillageDto, village: Village?): Village {
        var v = village;
        if (v == null) v = Village();
        v.nameEn = dto.nameEn
        v.nameBn = dto.nameBn
        v.union = dto.unionId?.let { this.unionService.find(it).orElseThrow { NotFoundException() } }
        return v
    }

}
