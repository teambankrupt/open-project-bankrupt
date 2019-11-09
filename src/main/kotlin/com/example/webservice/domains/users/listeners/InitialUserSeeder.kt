package com.example.webservice.domains.users.listeners

import com.example.webservice.commons.utils.PasswordUtil
import com.example.webservice.domains.users.models.entities.Privilege
import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.domains.users.models.entities.User
import com.example.webservice.domains.users.services.PrivilegeService
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.domains.users.services.UserService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class InitialUserSeeder @Autowired constructor(
        val privilegeService: PrivilegeService,
        val roleService: RoleService,
        val userService: UserService
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (!this.privilegeService.empty()) return;

        val privileges = this.createPrivilegesIfNotExists()

        val adminRole = Role()
        adminRole.name = "Admin"
        adminRole.privileges = privileges
        this.roleService.save(adminRole)

        val userRole = Role()
        userRole.name = "User"
        val userPrivilege = this.privilegeService.find(Privilege.Privileges.ACCESS_USER_RESOURCES.name).orElseThrow { NotFoundException("Could not find privilege for user: ${Privilege.Privileges.ACCESS_USER_RESOURCES.name}") }
        userRole.privileges = listOf(userPrivilege)
        this.roleService.save(userRole)

    }


    fun createPrivilegesIfNotExists(): List<Privilege> {
        val privileges: MutableList<Privilege> = ArrayList()

        Privilege.Privileges.values().forEach {
            val privilege = this.privilegeService.find(it.toString())
            if (!privilege.isPresent) {
                privileges.add(this.privilegeService.save(Privilege(it.name, it.label)))
            }
        }
        return privileges
    }
}