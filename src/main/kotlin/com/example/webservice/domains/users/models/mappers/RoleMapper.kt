package com.example.webservice.domains.users.models.mappers

import com.example.webservice.domains.users.models.dtos.RoleDto
import com.example.webservice.domains.users.models.entities.Role
import com.example.webservice.domains.users.services.PrivilegeService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoleMapper @Autowired constructor(
        private val privilegeMapper: PrivilegeMapper,
        private val privilegeService: PrivilegeService
) {

    fun map(role: Role): RoleDto {
        val dto = RoleDto()
        dto.id = role.id
        dto.created = role.createdAt
        dto.updatedAt = role.updatedAt

        dto.name = role.name
        dto.restricted = role.restricted
        dto.privileges = role.privileges?.map { privilege -> this.privilegeMapper.map(privilege) }
        return dto
    }

    fun map(dto: RoleDto, exRole: Role?): Role {
        val role = exRole ?: Role()

        role.name = dto.name
        role.restricted = dto.restricted
        role.privileges = dto.privilegeIds.map { privilegeId ->
            this.privilegeService.find(privilegeId)
                    .orElseThrow { NotFoundException("Could not find privilege with id: $privilegeId") }
        }
        return role
    }

}
