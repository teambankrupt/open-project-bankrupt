package com.example.virtualfilesystem.domains.vextensions.models.mappers

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
        val entity = exEntity ?: VExtension()

        entity.name = dto.name
        entity.ext = dto.ext
                .replace(" ", "")
                .toLowerCase()
        if (!entity.ext.startsWith("."))
            entity.ext = ".".plus(entity.ext)

        return entity
    }
}