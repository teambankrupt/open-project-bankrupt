package com.example.virtualfilesystem.domains.vfolders.services.beans

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import com.example.virtualfilesystem.domains.vfolders.repositories.VFolderRepository
import com.example.virtualfilesystem.domains.vfolders.services.VFolderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class VFolderServiceBean @Autowired constructor(
        private val vFolderRepository: VFolderRepository
) : VFolderService {

    override fun search(query: String, parentId: Long?, page: Int, size: Int): Page<VFolder> {
        if (parentId == null) return this.vFolderRepository.searchRootFolders(query, PageAttr.getPageRequest(page, size))
        return this.vFolderRepository.search(query, parentId, PageAttr.getPageRequest(page, size))
    }

    override fun search(query: String, page: Int, size: Int): Page<VFolder> {
        return this.vFolderRepository.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun save(entity: VFolder): VFolder {
        this.validate(entity)
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

    override fun validate(entity: VFolder) {
        if (entity.id != null) { // validation during update
            if (entity.id == entity.parent?.id) {
                ExceptionUtil.wtf("Have you seen \"Back to the future\" movie? What if Marty would get enticed by his own mommy and tried to conceive himself? Well, people would say, this could only mean a catastrophe.")
            }
        }
    }

}