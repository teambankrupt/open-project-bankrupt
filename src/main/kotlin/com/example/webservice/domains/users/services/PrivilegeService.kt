package com.example.webservice.domains.users.services

import com.example.webservice.domains.common.services.CrudService
import com.example.webservice.domains.users.models.entities.Privilege
import org.springframework.data.domain.Page
import java.util.*

interface PrivilegeService: CrudService<Privilege>{

    fun find(name: String): Optional<Privilege>

    fun empty(): Boolean

    fun findAll(): List<Privilege>
}
