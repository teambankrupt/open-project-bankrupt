package com.example.webservice.config

import com.example.webservice.commons.utils.PasswordUtil
import com.example.webservice.domains.users.models.dtos.UserDto
import com.example.webservice.domains.users.models.entities.Privilege
import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.domains.users.models.entities.User
import com.example.webservice.domains.users.models.mappers.UserMapper
import com.example.webservice.domains.users.services.PrivilegeService
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.domains.users.services.UserService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class InitConfig @Autowired constructor(
        val privilegeService: PrivilegeService,
        val roleService: RoleService,
        val userService: UserService,
        val userMapper: UserMapper
) {
    @Value("\${admin.username}")
    lateinit var adminUsername: String

    @Value("\${admin.password}")
    lateinit var adminPass: String

    @Value("\${admin.phone}")
    lateinit var adminPhone: String

    @Value("\${admin.email}")
    lateinit var adminEmail: String

    @Value("\${auth.method}")
    lateinit var authMethod: String


    @EventListener(ContextRefreshedEvent::class)
    fun onBootUp() {
        if (!this.privilegeService.empty()) return

        val privileges = this.createPrivilegesIfNotExists()

        val adminRole = Role()
        adminRole.name = "Admin"
        adminRole.privileges = privileges
        this.roleService.save(adminRole)

        val userRole = Role()
        userRole.name = "User"
        userRole.restricted = false
        val userPrivilege = this.privilegeService.find(Privilege.Privileges.ACCESS_USER_RESOURCES.name).orElseThrow { NotFoundException("Could not find privilege for user: ${Privilege.Privileges.ACCESS_USER_RESOURCES.name}") }
        userRole.privileges = listOf(userPrivilege)
        this.roleService.save(userRole)


        // create admin user
        val user = User()
        user.name = "Admin"
        user.username = this.adminUsername
        user.password = PasswordUtil.encryptPassword(this.adminPass, PasswordUtil.EncType.BCRYPT_ENCODER, null)
        user.phone = this.adminPhone
        user.email = this.adminEmail
        user.gender = "male"
        user.roles = ArrayList()
        user.roles.add(this.roleService.find(Role.ERole.Admin.name).orElseThrow { NotFoundException("Could not assign admin role to admin as it's not found!") })

        this.userService.save(user)

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
