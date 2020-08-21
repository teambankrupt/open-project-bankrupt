package com.example.virtualfilesystem.domains.vextensions.models.mappers

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.virtualfilesystem.domains.vextensions.models.dtos.VExtensionDto
import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import org.springframework.stereotype.Component

@Component
class VExtensionMapper : BaseMapper<VExtension, VExtensionDto> {

    override fun map(entity: VExtension): VExtensionDto {
        val dto = VExtensionDto()
        dto.id = entity.id
        dto.createdAt = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.name = entity.name
        dto.ext = entity.ext

        return dto
    }

    override fun map(dto: VExtensionDto, exEntity: VExtension?): VExtension {
        throw ExceptionUtil.forbidden("This operation is not allowed at this time.")
    }
}