package com.example.virtualfilesystem.domains.vextensions.services.beans

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import com.example.virtualfilesystem.domains.vextensions.repositories.VExtensionRepository
import com.example.virtualfilesystem.domains.vextensions.services.VExtensionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class VExtensionServiceBean @Autowired constructor(
        private val vExtensionRepository: VExtensionRepository
) : VExtensionService {

    override fun search(query: String, page: Int, size: Int): Page<VExtension> {
        return this.vExtensionRepository.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun save(entity: VExtension): VExtension {
        this.validate(entity)
        return this.vExtensionRepository.save(entity)
    }

    override fun find(id: Long): Optional<VExtension> {
        return this.vExtensionRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("VExtension", id) }
            entity.isDeleted = true
            this.vExtensionRepository.save(entity)
        }
        this.vExtensionRepository.deleteById(id)
    }

    private fun validate(entity: VExtension) {

        // For new entity
        if (entity.id == null) {
            val ex = this.vExtensionRepository.find(entity.ext)
            if (ex.isPresent)
                throw ExceptionUtil.invalid("Extension already exists with code: ${ex.get().ext}")
        }
    }

}