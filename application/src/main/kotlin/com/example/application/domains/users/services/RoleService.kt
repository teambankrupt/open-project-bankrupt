package com.example.application.domains.users.services


import com.example.coreweb.domains.base.services.CrudService
import com.example.application.domains.users.models.entities.Role
import com.example.common.exceptions.forbidden.ForbiddenException
import com.example.common.exceptions.notfound.UserNotFoundException
import java.util.*

interface RoleService : CrudService<Role> {
    fun find(name: String): Optional<Role>
    fun findUnrestricted(name: String): Optional<Role>
    fun findByIds(roleIds: List<Long>): List<Role>
    fun findByIdsUnrestricted(roleIds: List<Long>): List<Role>

    @Throws(ForbiddenException::class, UserNotFoundException::class)
    fun findByUser(userId: Long): List<Role>

    fun findAll(): List<Role>
    fun delete(id: Long)
}
