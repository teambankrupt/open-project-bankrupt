package com.example.app.domains.crudexample.repositories

import com.example.app.domains.crudexample.models.entities.CrudExample
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CrudExampleRepository : JpaRepository<CrudExample, Long> {

    @Query("SELECT e FROM CrudExample e WHERE (:q IS NULL OR e.createdBy LIKE %:q%) AND e.deleted=FALSE")
    fun search(@Param("q") query: String, pageable: Pageable): Page<CrudExample>

    @Query("SELECT e FROM CrudExample e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<CrudExample>

}