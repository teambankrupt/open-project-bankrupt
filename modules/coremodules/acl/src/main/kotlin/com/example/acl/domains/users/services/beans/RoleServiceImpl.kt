package com.example.acl.domains.users.services.beans

import com.example.coreweb.utils.PageAttr
import com.example.common.utils.ExceptionUtil
import com.example.auth.entities.Role
import com.example.acl.domains.users.repositories.RoleRepository
import com.example.acl.domains.users.repositories.UserRepository
import com.example.acl.domains.users.services.RoleService
import com.example.common.exceptions.exists.AlreadyExistsException
import com.example.common.exceptions.forbidden.ForbiddenException
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleServiceImpl @Autowired constructor(
        private val userRepo: UserRepository,
        private val roleRepo: RoleRepository
) : RoleService {

    override fun find(id: Long): Optional<Role> {
        return this.roleRepo.find(id)
    }

    override fun search(query: String, page: Int, size: Int): Page<Role> {
        return this.roleRepo.search(query, PageAttr.getPageRequest(page,size));
    }

    override fun findByIds(roleIds: List<Long>): List<Role> {
        return this.roleRepo.findByRoleIds(roleIds)
    }

    override fun findByIdsUnrestricted(roleIds: List<Long>): List<Role> {
        return this.roleRepo.findByRoleIdsUnrestricted(roleIds)
    }

    override fun find(name: String): Optional<Role> {
        return this.roleRepo.find(name)
    }

    override fun findUnrestricted(name: String): Optional<Role> {
        return this.roleRepo.findUnrestricted(name)
    }

    override fun save(entity: Role): Role {
        // When creating new role check if already exists with same name
        if (entity.id == null) {
            if (this.roleRepo.existsByName(entity.name))
                throw AlreadyExistsException("Role exists with name: ${entity.name}")
        }
        return this.roleRepo.save(entity)
    }

    @Throws(ForbiddenException::class, UserNotFoundException::class)
    override fun findByUser(userId: Long): List<Role> {
        val user = this.userRepo.findById(userId).orElseThrow { UserNotFoundException("Could not find user with id $userId") }
        return user.roles
    }

    override fun findAll(): List<Role> {
        return this.roleRepo.findAll()
    }

    override fun delete(id: Long) {
        val role = this.roleRepo.find(id)
        if (!role.isPresent) throw InvalidException("Role doesn't exist with id: $id")
        this.roleRepo.delete(role.get())
    }

    override fun delete(id: Long, softDelete: Boolean) {
        val role = this.roleRepo.find(id).orElseThrow { ExceptionUtil.notFound("role", id) }

        if (softDelete) {
            role.isDeleted = true
            this.roleRepo.save(role)
            return
        }
        role.privileges = emptyList()
        this.roleRepo.save(role)
        this.roleRepo.delete(role)
    }
}
