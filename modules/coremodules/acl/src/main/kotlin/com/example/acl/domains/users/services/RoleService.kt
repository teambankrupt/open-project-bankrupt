package com.example.acl.domains.users.services

import com.example.auth.entities.Role
import com.example.common.exceptions.forbidden.ForbiddenException
import com.example.common.exceptions.notfound.UserNotFoundException
import org.springframework.data.domain.Page
import java.util.*

interface RoleService  {
    fun search(query: String, page: Int, size: Int): Page<Role>
    fun save(entity: Role): Role
    fun find(id: Long): Optional<Role>
    fun delete(id: Long, softDelete: Boolean)

    fun find(name: String): Optional<Role>
    fun findUnrestricted(name: String): Optional<Role>
    fun findByIds(roleIds: List<Long>): List<Role>
    fun findByIdsUnrestricted(roleIds: List<Long>): List<Role>

    @Throws(ForbiddenException::class, UserNotFoundException::class)
    fun findByUser(userId: Long): List<Role>

    fun findAll(): List<Role>
    fun delete(id: Long)
}
