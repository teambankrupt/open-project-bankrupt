package com.example.webservice.domains.users.services

import com.example.webservice.domains.users.models.entities.User
import java.util.*

interface UserServiceV2 {
    fun register(token: String, user: User): User
    fun requireAccountValidationByOTP(phoneOrEmail: String, tokenValidUntil: Date): Boolean
    fun findByUsername(username: String): Optional<User>
    fun findByPhone(phone: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
}
