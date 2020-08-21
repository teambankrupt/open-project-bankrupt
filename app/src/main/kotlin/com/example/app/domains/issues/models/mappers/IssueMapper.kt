package com.example.app.domains.issues.models.mappers

import com.example.app.domains.issues.models.dtos.IssueDto
import com.example.app.domains.issues.models.entities.Issue
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.virtualfilesystem.domains.vextensions.services.VExtensionService
import com.example.virtualfilesystem.domains.vfolders.services.VFolderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IssueMapper @Autowired constructor(
        private val vExtensionService: VExtensionService,
        private val folderService: VFolderService
) : BaseMapper<Issue, IssueDto> {

    override fun map(entity: Issue): IssueDto {
        val dto = IssueDto()
        dto.id = entity.id
        dto.createdAt = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.number = entity.number
        dto.folderId = entity.folder.id

        return dto
    }

    override fun map(dto: IssueDto, exEntity: Issue?): Issue {
        val entity = exEntity ?: Issue()

        entity.number = dto.number
        entity.name = dto.number
        entity.ext = this.vExtensionService.findOrCreate(Issue::class.java)
        entity.folder = this.folderService.find(dto.folderId).orElseThrow { ExceptionUtil.notFound("Folder", dto.folderId) }

        return entity
    }
}