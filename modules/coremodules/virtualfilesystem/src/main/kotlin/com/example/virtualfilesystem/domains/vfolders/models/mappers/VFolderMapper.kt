package com.example.virtualfilesystem.domains.vfolders.models.mappers

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.virtualfilesystem.domains.vfolders.models.dtos.VFolderDto
import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import com.example.virtualfilesystem.domains.vfolders.repositories.VFolderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VFolderMapper @Autowired constructor(
        private val vFolderRepository: VFolderRepository
) : BaseMapper<VFolder, VFolderDto> {

    override fun map(entity: VFolder): VFolderDto {
        val dto = VFolderDto()
        dto.id = entity.id
        dto.createdAt = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.name = entity.name
        dto.description = entity.description
        dto.thumbnail = entity.thumbnail
        dto.path = entity.path
        dto.accentColor = entity.accentColor
        dto.parentId = entity.parent?.id
        dto.rootId = entity.getRootId()

        return dto
    }

    override fun map(dto: VFolderDto, exEntity: VFolder?): VFolder {
        val entity = exEntity ?: VFolder()

        entity.name = dto.name
        entity.description = dto.description
        entity.thumbnail = dto.thumbnail
        entity.accentColor = dto.accentColor
        entity.parent = dto.parentId?.let { this.vFolderRepository.find(it).orElseThrow { ExceptionUtil.notFound("VFolder", dto.id!!) } }

        return entity
    }
}