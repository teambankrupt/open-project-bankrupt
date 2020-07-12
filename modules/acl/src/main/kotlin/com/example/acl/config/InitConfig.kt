package com.example.acl.config


import com.example.auth.entities.Privilege
import com.example.auth.entities.Role
import com.example.auth.entities.User
import com.example.acl.domains.users.services.PrivilegeService
import com.example.acl.domains.users.services.RoleService
import com.example.acl.domains.users.services.UserService
import com.example.auth.enums.Privileges
import com.example.auth.enums.Roles
import com.example.auth.utils.PasswordUtil
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
@PropertySource("classpath:security.properties")
class InitConfig @Autowired constructor(
        private val privilegeService: PrivilegeService,
        private val roleService: RoleService,
        private val userService: UserService
) {
    @Value("\${auth.admin.username}")
    lateinit var adminUsername: String

    @Value("\${auth.admin.password}")
    lateinit var adminPass: String

    @Value("\${auth.admin.phone}")
    lateinit var adminPhone: String

    @Value("\${auth.admin.email}")
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
        userRole.isRestricted = false
        val userPrivilege = this.privilegeService.find(Privileges.ACCESS_USER_RESOURCES.name).orElseThrow { NotFoundException("Could not find privilege for user: ${Privileges.ACCESS_USER_RESOURCES.name}") }
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
        user.roles.add(this.roleService.find(Roles.Admin.name).orElseThrow { NotFoundException("Could not assign admin role to admin as it's not found!") })

        if ("phone" == this.authMethod && (user.phone == null || user.phone.isEmpty()))
            throw RuntimeException("You've chooses `phone` number as authentication method, but forgot to provide admin phone number in security.properties?")
        if ("email" == this.authMethod && (user.email == null || user.email.isEmpty()))
            throw RuntimeException("You've chooses `email` number as authentication method, but forgot to provide admin email in security.properties?")

        this.userService.save(user)

    }

    private fun createPrivilegesIfNotExists(): List<Privilege> {
        val privileges: MutableList<Privilege> = ArrayList()

        Privileges.values().forEach {
            val privilege = this.privilegeService.find(it.toString())
            if (!privilege.isPresent) {
                privileges.add(this.privilegeService.save(Privilege(it.name, it.label)))
            }
        }
        return privileges
    }
}
