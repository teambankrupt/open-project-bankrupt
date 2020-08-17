package com.example.virtualfilesystem.domains.vfolders.repositories

import com.example.virtualfilesystem.domains.vfolders.models.entities.VFolder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VFolderRepository : JpaRepository<VFolder, Long> {

    @Query("SELECT e FROM VFolder e WHERE (:q IS NULL OR e.createdBy LIKE %:q%) AND e.deleted=FALSE")
    fun search(@Param("q") query: String, pageable: Pageable): Page<VFolder>

    @Query("SELECT e FROM VFolder e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<VFolder>

}