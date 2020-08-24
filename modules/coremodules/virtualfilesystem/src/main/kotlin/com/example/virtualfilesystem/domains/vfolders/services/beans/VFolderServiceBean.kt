package com.example.virtualfilesystem.domains.vfolders.services.beans

import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import com.example.virtualfilesystem.domains.vfolders.repositories.VFolderRepository
import com.example.virtualfilesystem.domains.vfolders.services.VFolderService
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class VFolderServiceBean @Autowired constructor(
        private val vFolderRepository: VFolderRepository
) : VFolderService {

    override fun search(query: String, page: Int, size: Int): Page<VFolder> {
        return this.vFolderRepository.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun save(entity: VFolder): VFolder {
        return this.vFolderRepository.save(entity)
    }

    override fun find(id: Long): Optional<VFolder> {
        return this.vFolderRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("VFolder", id) }
            entity.isDeleted = true
            this.vFolderRepository.save(entity)
        }
        this.vFolderRepository.deleteById(id)
    }

}