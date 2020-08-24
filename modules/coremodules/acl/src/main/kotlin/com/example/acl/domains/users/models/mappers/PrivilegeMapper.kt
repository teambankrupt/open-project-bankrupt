package com.example.acl.domains.users.models.mappers

import com.example.acl.domains.users.models.dtos.PrivilegeDto
import com.example.auth.entities.Privilege
import org.springframework.stereotype.Component

@Component
class PrivilegeMapper {
    fun map(privilege: Privilege): PrivilegeDto {
        val dto = PrivilegeDto()
        dto.id = privilege.id
        dto.createdAt = privilege.createdAt
        dto.updatedAt = privilege.updatedAt

        dto.label = privilege.label
        dto.name = privilege.name
        dto.description = privilege.description
        dto.accessUrls = privilege.accessUrls
        return dto
    }

    fun map(dto: PrivilegeDto, exPrivilege: Privilege?): Privilege {
        var privilege = exPrivilege
        if (privilege == null) privilege = Privilege()

        privilege.label = dto.label
        privilege.name = dto.name.replace(" ", "_").toUpperCase()
        privilege.description = dto.description
        privilege.accessUrls = dto.accessUrls
        return privilege
    }

}
