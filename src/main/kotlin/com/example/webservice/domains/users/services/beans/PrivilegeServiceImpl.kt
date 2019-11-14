package com.example.webservice.domains.users.services.beans

import com.example.webservice.domains.users.models.entities.Privilege
import com.example.webservice.domains.users.repositories.PrivilegeRepository
import com.example.webservice.domains.users.services.PrivilegeService
import com.example.webservice.exceptions.exists.AlreadyExistsException
import com.example.webservice.exceptions.forbidden.ForbiddenException
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
        // When creating new privilege check if already exists with same name
        if (privilege.id == null) {
            if (this.privilegeRepo.existsByName(privilege.name)
                    || this.privilegeRepo.existsByLabel(privilege.label))
                throw AlreadyExistsException("Privilege with same name or label already exists!")
        }
        return this.privilegeRepo.save(privilege)
    }

    override fun find(id: Long): Optional<Privilege> {
        return this.privilegeRepo.find(id)
    }

    override fun search(q: String): List<Privilege> {
        return this.privilegeRepo.search(q)
    }

    override fun delete(id: Long) {
        if (!this.privilegeRepo.existsById(id)) throw ForbiddenException("Privilege doesn't exist for id: $id")
        this.privilegeRepo.deleteById(id)
    }

}
