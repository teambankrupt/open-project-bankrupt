package com.example.webservice.domains.users.services


import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.exceptions.forbidden.ForbiddenException
import com.example.webservice.exceptions.notfound.UserNotFoundException

interface RoleService {
    fun find(name: String): Role
    fun findByIds(roleIds: List<Long>): List<Role>
    fun save(role: Role): Role

    @Throws(ForbiddenException::class, UserNotFoundException::class)
    fun findByUser(userId: Long): List<Role>
}
