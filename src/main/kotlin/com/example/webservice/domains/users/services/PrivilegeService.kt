package com.example.webservice.domains.users.services

import com.example.webservice.domains.users.models.entities.Privilege
import java.util.*

interface PrivilegeService {
    fun save(privilege: Privilege): Privilege

    fun find(id: Long): Optional<Privilege>
    fun find(name: String): Optional<Privilege>

    fun search(q: String): List<Privilege>

    fun delete(id: Long)

    fun empty(): Boolean
}
