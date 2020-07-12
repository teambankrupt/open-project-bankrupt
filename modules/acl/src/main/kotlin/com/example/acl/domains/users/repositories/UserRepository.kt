package com.example.acl.domains.users.repositories

import com.example.auth.entities.Role
import com.example.auth.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE (:q IS NULL OR (u.username LIKE %:q% OR u.name LIKE %:q%)) AND u.deleted=false")
    fun search(@Param("q") query: String, pageable: Pageable): Page<User>

    @Query("SELECT u FROM User u WHERE (:q IS NULL OR (u.username LIKE %:q% OR u.name LIKE %:q%)) AND (:role IS NULL OR :role MEMBER OF u.roles) AND u.deleted=false")
    fun search(@Param("q") query: String, @Param("role") role: Role?, pageable: Pageable): Page<User>

    @Query("SELECT u FROM User u WHERE u.id=:id AND u.deleted=false")
    fun find(@Param("id") id: Long): Optional<User>

    fun findByUsername(username: String): Optional<User>
    fun findByPhone(phone: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

    fun findByRolesName(role: String, pageable: Pageable): Page<User>

    fun findByRolesName(role: String): List<User>

    @Modifying
    @Query("UPDATE User u SET u.enabled=:enabled WHERE u.id=:userId")
    fun toggleAccess(@Param("userId") userId: Long, @Param("enabled") enabled: Boolean)
}
