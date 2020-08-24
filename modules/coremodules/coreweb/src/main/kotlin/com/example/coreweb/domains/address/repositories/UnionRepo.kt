package com.example.coreweb.domains.address.repositories

import com.example.coreweb.domains.address.models.entities.Union
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UnionRepo:JpaRepository<Union,Long> {

    @Query("SELECT u FROM Union u WHERE (:q IS NULL OR u.nameEn LIKE %:q%) AND u.deleted = false")
    fun search(@Param("q") query: String, pagable: Pageable): Page<Union>

    @Query("SELECT u FROM Union u WHERE u.id = :id AND u.deleted = false")
    fun find(@Param("id") id: Long) : Optional<Union>
}
