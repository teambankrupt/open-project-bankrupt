package com.example.virtualfilesystem.domains.vfiles.daos

import com.example.coreweb.utils.PageAttr
import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import com.example.virtualfilesystem.domains.vextensions.repositories.VExtensionRepository
import com.example.virtualfilesystem.domains.vfiles.models.VFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.EntityManager

@Component
class VFileDao @Autowired constructor(
        private val entityManager: EntityManager,
        private val vExtensionRepository: VExtensionRepository
) {


    fun getFiles(folderId: Long, page: Int, size: Int): List<VFile> {
        val pageable = PageAttr.getPageRequest(page, size)
        val extensions = this.vExtensionRepository.findAll()
        return extensions.map {
            this.filesOfExt(it, folderId, pageable)
        }.flatten()
    }

    private fun filesOfExt(ext: VExtension, folderId: Long, pageable: Pageable): List<VFile> {
        val sql = "SELECT e FROM ${ext.ext} e WHERE e.ext.id=:extId AND e.folder.id=:folderId AND e.deleted = FALSE"
        val query = entityManager.createQuery(sql)
                .setFirstResult(pageable.pageNumber * pageable.pageSize)
                .setMaxResults(pageable.pageSize)
        query.setParameter("extId", ext.id)
        query.setParameter("folderId", folderId)

        return try {
            query.resultList as List<VFile>
        } catch (e: Exception) {
            ArrayList()
        }
    }


}