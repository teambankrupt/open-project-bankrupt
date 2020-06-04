package com.example.webservice.domains.users.services

import com.example.webservice.domains.common.base.services.CrudService
import com.example.webservice.domains.users.models.entities.User
import org.springframework.data.domain.Page
import java.util.*
import javax.transaction.Transactional

interface UserService : CrudService<User> {
    fun search(query: String, role: String?, page: Int, size: Int): Page<User>
    fun findAll(page: Int): Page<User>
    fun findByRole(role: String, page: Int): Page<User>
    fun findByRole(role: String): List<User>

    fun register(token: String, user: User): User
    fun requireAccountValidationByOTP(phoneOrEmail: String, tokenValidUntil: Date): Boolean
    fun findByUsername(username: String): Optional<User>
    fun findByPhone(phone: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

    fun changePassword(id: Long, currentPassword: String, newPassword: String): User
    fun setPassword(id: Long, newPassword: String): User
    fun handlePasswordResetRequest(username: String)
    fun setRoles(id: Long, roleIds: List<Long>): User

    @Transactional
    fun resetPassword(username: String, token: String, password: String): User

    fun toggleAccess(userId: Long, enable: Boolean)
}
