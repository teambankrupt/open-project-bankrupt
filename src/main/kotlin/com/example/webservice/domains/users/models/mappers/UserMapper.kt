package com.example.webservice.domains.users.models.mappers

import com.example.webservice.commons.utils.PasswordUtil
import com.example.webservice.domains.users.models.dtos.UserDto
import com.example.webservice.domains.users.models.entities.User
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.domains.users.services.UserService
import com.example.webservice.domains.users.services.UserServiceV2
import com.example.webservice.exceptions.exists.AlreadyExistsException
import com.example.webservice.exceptions.invalid.InvalidException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UserMapper @Autowired constructor(
        val userService: UserServiceV2,
        val roleService: RoleService
) {
    @Value("\${auth.method}")
    lateinit var authMethod: String

    fun map(dto: UserDto, exUser: User?): User {
        val user = exUser ?: User()
        user.name = dto.name
        user.phone = dto.phone
        user.username = dto.username
        user.password = PasswordUtil.encryptPassword(dto.password, PasswordUtil.EncType.BCRYPT_ENCODER, null)
        user.email = dto.email
        user.roles = listOf(this.roleService.find(dto.role))
        this.validate(user)
        return user
    }

    fun map(user: User): UserDto {
        val dto = UserDto()
        dto.name = user.name
        dto.username = user.username
        dto.phone = user.phone
        dto.email = user.email
        return dto
    }

    fun validate(user: User) {
        if (user.id == null) { // For new user
            if (this.userService.findByUsername(user.username).isPresent) throw AlreadyExistsException("User already exists with username: ${user.username}")
            if (authMethod == "phone") {
                if (user.phone == null || user.phone.isEmpty()) throw InvalidException("Phone number can't be null or empty!")
                if (this.userService.findByPhone(user.phone).isPresent) throw AlreadyExistsException("User already exists with phone: ${user.phone}")
            } else {
                if (user.email == null || user.email.isEmpty()) throw InvalidException("Email can't be null or empty!")
                if (this.userService.findByEmail(user.email).isPresent) throw AlreadyExistsException("User already exists with email: ${user.email}")

            }
        }
    }
}
