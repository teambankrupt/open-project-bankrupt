package com.example.webservice.domains.address.models.mappers

import com.example.webservice.domains.address.models.dto.UnionDto
import com.example.webservice.domains.address.models.entities.Union
import com.example.webservice.domains.address.services.UpazilaService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UnionMapper(@Autowired val upazilaService: UpazilaService) {


    fun map(union: Union): UnionDto {
        val unionDto = UnionDto()
        unionDto.id = union.id
        unionDto.nameEn = union.nameEn
        unionDto.nameBn = union.nameBn
        unionDto.created = union.created
        unionDto.lastUpdated = union.lastUpdated
        unionDto.upazilaId = union.upazila?.id
        return unionDto
    }

    fun map(unionDto: UnionDto, u: Union?): Union {
        var union = u
        if (union == null) union = Union()

        union.nameEn = unionDto.nameEn
        union.nameBn = unionDto.nameBn
        union.upazila = unionDto.upazilaId?.let { upazilaService.find(it).orElseThrow { NotFoundException() } }
        return union

    }


}
