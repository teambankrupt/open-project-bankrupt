package com.example.virtualfilesystem.domains.vfiles.daos

import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import com.example.virtualfilesystem.domains.vextensions.repositories.VExtensionRepository
import com.example.virtualfilesystem.domains.vfiles.models.VFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.EntityManager

@Component
class VFileDao @Autowired constructor(
        private val entityManager: EntityManager,
        private val vExtensionRepository: VExtensionRepository
) {


    fun getFiles(folderId: Long): List<VFile> {
        val extensions = this.vExtensionRepository.findAll()
        return extensions.map {
            this.filesOfExt(it, folderId)
        }.flatten()
    }

    private fun filesOfExt(ext: VExtension, folderId: Long): List<VFile> {
        val sql = "SELECT * FROM ${ext.ext} e WHERE e.id=:${ext.id} AND e.folder.id=:folderId AND e.deleted = FALSE"
        val query = entityManager.createQuery(sql)

        return try {
            query.resultList as List<VFile>
        } catch (e: Exception) {
            ArrayList()
        }
    }


}