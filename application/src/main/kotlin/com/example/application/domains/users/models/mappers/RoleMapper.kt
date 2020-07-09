package com.example.application.domains.users.models.mappers

import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.application.domains.users.models.dtos.RoleDto
import com.example.application.domains.users.models.entities.Role
import com.example.application.domains.users.services.PrivilegeService
import com.example.common.exceptions.notfound.NotFoundException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoleMapper @Autowired constructor(
        private val privilegeMapper: PrivilegeMapper,
        private val privilegeService: PrivilegeService
) : BaseMapper<Role, RoleDto> {

    override fun map(entity: Role): RoleDto {
        val dto = RoleDto()
        dto.id = entity.id
        dto.created = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.name = entity.name
        dto.description = entity.description
        dto.restricted = entity.restricted
        dto.privileges = entity.privileges?.map { privilege -> this.privilegeMapper.map(privilege) }
        return dto
    }

    override fun map(dto: RoleDto, exEntity: Role?): Role {
        val role = exEntity ?: Role()

        role.name = dto.name
        role.description = dto.description
        role.restricted = dto.restricted
        role.privileges = dto.privilegeIds.map { privilegeId ->
            this.privilegeService.find(privilegeId)
                    .orElseThrow { NotFoundException("Could not find privilege with id: $privilegeId") }
        }
        return role
    }

}
