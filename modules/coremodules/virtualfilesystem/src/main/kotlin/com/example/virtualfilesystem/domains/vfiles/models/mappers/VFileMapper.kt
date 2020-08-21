package com.example.virtualfilesystem.domains.vfiles.models.mappers

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.virtualfilesystem.domains.vfiles.models.VFile
import com.example.virtualfilesystem.domains.vfiles.models.dtos.VFileDto
import org.springframework.stereotype.Component

@Component
class VFileMapper : BaseMapper<VFile, VFileDto> {

    override fun map(entity: VFile): VFileDto {
        val dto = VFileDto()
        dto.id = entity.id
        dto.createdAt = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.name = entity.name
        dto.hidden = entity.hidden
        dto.extId = entity.ext.id
        dto.ext = entity.ext.ext
        dto.folderId = entity.folder.id

        return dto
    }

    override fun map(dto: VFileDto, exEntity: VFile?): VFile {
        throw ExceptionUtil.forbidden("This operation isn't allowed!")
    }
}