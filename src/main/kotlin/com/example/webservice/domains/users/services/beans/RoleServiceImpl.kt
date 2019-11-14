package com.example.webservice.domains.users.services.beans

import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.domains.users.repositories.RoleRepository
import com.example.webservice.domains.users.repositories.UserRepository
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.exceptions.forbidden.ForbiddenException
import com.example.webservice.exceptions.notfound.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoleServiceImpl @Autowired
constructor(private val userRepo: UserRepository, private val roleRepo: RoleRepository) : RoleService {

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
        return this.roleRepo.save(role)
    }

    @Throws(ForbiddenException::class, UserNotFoundException::class)
    override fun findByUser(userId: Long): List<Role> {
        val user = this.userRepo.findById(userId).orElseThrow { UserNotFoundException("Could not find user with id $userId") }
        return user.roles
    }
}
