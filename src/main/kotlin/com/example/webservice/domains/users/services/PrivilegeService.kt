package com.example.webservice.domains.users.services

import com.example.webservice.domains.common.sms.services.CrudService
import com.example.webservice.domains.users.models.entities.Privilege
import java.util.*

interface PrivilegeService: CrudService<Privilege>{

    fun find(name: String): Optional<Privilege>

    fun empty(): Boolean

    fun findAll(): List<Privilege>
}
