package com.example.acl.domains.users.services

import com.example.auth.entities.Privilege
import org.springframework.data.domain.Page
import java.util.*

interface PrivilegeService {
    fun search(query: String, page: Int, size: Int): Page<Privilege>
    fun save(entity: Privilege): Privilege
    fun find(id: Long): Optional<Privilege>
    fun delete(id: Long, softDelete: Boolean)

    fun find(name: String): Optional<Privilege>

    fun empty(): Boolean

    fun findAll(): List<Privilege>
}
