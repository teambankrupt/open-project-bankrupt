package com.example.webservice.domains.users.services.beans

import com.example.webservice.domains.users.models.entities.Privilege
import com.example.webservice.domains.users.repositories.PrivilegeRepository
import com.example.webservice.domains.users.services.PrivilegeService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PrivilegeServiceImpl @Autowired constructor(
        private val privilegeRepo: PrivilegeRepository
) : PrivilegeService {
    override fun empty(): Boolean {
        return this.privilegeRepo.count() == 0L
    }

    override fun find(name: String): Optional<Privilege> {
        return this.privilegeRepo.find(name)
    }

    override fun save(privilege: Privilege): Privilege {
        return this.privilegeRepo.save(privilege)
    }

    override fun find(id: Long): Optional<Privilege> {
        return this.privilegeRepo.find(id)
    }

    override fun search(q: String): List<Privilege> {
        return this.privilegeRepo.search(q)
    }

    override fun delete(id: Long) {
        val privilege = this.privilegeRepo.find(id).orElseThrow { NotFoundException("Could not find privilege with id: $id") }
        privilege.isDeleted = true
        this.save(privilege)
    }

}
