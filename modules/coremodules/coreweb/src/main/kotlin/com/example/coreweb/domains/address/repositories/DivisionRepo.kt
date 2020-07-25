package com.example.coreweb.domains.address.repositories

import com.example.coreweb.domains.address.models.entities.Division
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DivisionRepo : JpaRepository<Division, Long> {
    @Query("SELECT d FROM Division d WHERE (:q IS NULL OR d.nameEn LIKE %:q%) AND d.deleted = false")
    fun search(@Param("q") query: String, pagable: Pageable): Page<Division>

    @Query("SELECT d FROM Division d WHERE d.id = :id AND d.deleted = false")
    fun find(@Param("id") id: Long) : Optional<Division>
}
