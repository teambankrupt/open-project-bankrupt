package com.example.webservice.domains.users.services


import com.example.webservice.domains.common.services.CrudService
import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.exceptions.forbidden.ForbiddenException
import com.example.webservice.exceptions.notfound.UserNotFoundException
import org.springframework.data.domain.Page
import java.util.*

interface RoleService : CrudService<Role>{
    fun find(name: String): Optional<Role>
    fun findUnrestricted(name: String): Optional<Role>
    fun findByIds(roleIds: List<Long>): List<Role>

    @Throws(ForbiddenException::class, UserNotFoundException::class)
    fun findByUser(userId: Long): List<Role>

    fun findAll(): List<Role>
    fun delete(id: Long)
}
