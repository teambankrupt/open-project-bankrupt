package com.example.webservice.domains.users.services.beans

import com.example.webservice.commons.PageAttr
import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.domains.users.repositories.RoleRepository
import com.example.webservice.domains.users.repositories.UserRepository
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.exceptions.exists.AlreadyExistsException
import com.example.webservice.exceptions.forbidden.ForbiddenException
import com.example.webservice.exceptions.invalid.InvalidException
import com.example.webservice.exceptions.notfound.NotFoundException
import com.example.webservice.exceptions.notfound.UserNotFoundException
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

    override fun findAll(page: Int): Page<Role> {
        return this.roleRepo.findAll(PageAttr.getPageRequest(page));
    }

    override fun findByIds(roleIds: List<Long>): List<Role> {
        return this.roleRepo.findByRoleIds(roleIds)
    }

    override fun find(name: String): Optional<Role> {
        return this.roleRepo.find(name)
    }

    override fun findUnrestricted(name: String): Optional<Role> {
        return this.roleRepo.findUnrestricted(name)
    }

    override fun save(role: Role): Role {
        // When creating new role check if already exists with same name
        if (role.id == null) {
            if (this.roleRepo.existsByName(role.name))
                throw AlreadyExistsException("Role exists with name: ${role.name}")
        }
        return this.roleRepo.save(role)
    }

    @Throws(ForbiddenException::class, UserNotFoundException::class)
    override fun findByUser(userId: Long): List<Role> {
        val user = this.userRepo.findById(userId).orElseThrow { UserNotFoundException("Could not find user with id $userId") }
        return user.roles
    }

    override fun delete(id: Long) {
        val role = this.roleRepo.find(id)
        if (!role.isPresent) throw InvalidException("Role doesn't exist with id: $id")
        this.roleRepo.delete(role.get())
    }
}
