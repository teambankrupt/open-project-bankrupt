package com.example.webservice.domains.users.repositories

import com.example.webservice.domains.users.models.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.id=:id AND r.deleted=false")
    fun find(@Param("id") id: Long): Optional<Role>

    @Query("SELECT r FROM Role r WHERE r.name=:name AND r.deleted=false")
    fun find(@Param("name") name: String): Optional<Role>

    @Query("SELECT r from Role r WHERE r.id IN :ids AND r.deleted=false")
    fun findByRoleIds(@Param("ids") roleIds: List<Long>): List<Role>
}
