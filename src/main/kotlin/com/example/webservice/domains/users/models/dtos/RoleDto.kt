package com.example.webservice.domains.users.models.dtos

import com.example.webservice.domains.common.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty

class RoleDto : BaseDto() {
    lateinit var name: String

    var restricted: Boolean = true

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var privileges: List<PrivilegeDto>? = null

    @JsonProperty(value = "privilege_ids", access = JsonProperty.Access.WRITE_ONLY)
    lateinit var privilegeIds: List<Long>


}
