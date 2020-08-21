package com.example.app.domains.issues.repositories

import com.example.app.domains.issues.models.entities.Issue
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IssueRepository : JpaRepository<Issue, Long> {

    @Query("SELECT e FROM Issue e WHERE (:q IS NULL OR e.createdBy LIKE %:q%) AND e.deleted=FALSE")
    fun search(@Param("q") query: String, pageable: Pageable): Page<Issue>

    @Query("SELECT e FROM Issue e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<Issue>

}