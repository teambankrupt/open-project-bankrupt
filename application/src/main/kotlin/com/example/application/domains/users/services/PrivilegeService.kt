package com.example.application.domains.users.services

import com.example.coreweb.domains.base.services.CrudService
import com.example.application.domains.users.models.entities.Privilege
import java.util.*

interface PrivilegeService: CrudService<Privilege> {

    fun find(name: String): Optional<Privilege>

    fun empty(): Boolean

    fun findAll(): List<Privilege>
}
