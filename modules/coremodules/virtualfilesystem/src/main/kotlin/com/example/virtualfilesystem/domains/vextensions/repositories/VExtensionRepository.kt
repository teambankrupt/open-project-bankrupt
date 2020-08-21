package com.example.virtualfilesystem.domains.vextensions.repositories

import com.example.virtualfilesystem.domains.vextensions.models.entities.VExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VExtensionRepository : JpaRepository<VExtension, Long> {

    @Query("SELECT e FROM VExtension e WHERE (:q IS NULL OR e.createdBy LIKE %:q%) AND e.deleted=FALSE")
    fun search(@Param("q") query: String, pageable: Pageable): Page<VExtension>

    @Query("SELECT e FROM VExtension e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<VExtension>

    @Query("SELECT e FROM VExtension e WHERE e.ext=:ext AND e.deleted=FALSE")
    fun find(@Param("ext") ext: String): Optional<VExtension>

}