package com.example.webservice.domains.users.models.mappers

import com.example.webservice.domains.users.models.dtos.PrivilegeDto
import com.example.webservice.domains.users.models.entities.Privilege
import org.springframework.stereotype.Component

@Component
class PrivilegeMapper {
    fun map(privilege: Privilege): PrivilegeDto {
        val dto = PrivilegeDto()
        dto.id = privilege.id
        dto.created = privilege.created
        dto.lastUpdated = privilege.lastUpdated

        dto.name = privilege.name
        dto.label = privilege.label
        return dto
    }

    fun map(dto: PrivilegeDto, exPrivilege: Privilege?): Privilege {
        var privilege = exPrivilege;
        if (privilege == null) privilege = Privilege()

        privilege.name = dto.name
        privilege.label = dto.label
        return privilege
    }

}