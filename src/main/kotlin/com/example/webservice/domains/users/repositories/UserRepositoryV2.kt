package com.example.webservice.domains.users.repositories

import com.example.webservice.domains.users.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepositoryV2 : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun findByPhone(phone: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
}
