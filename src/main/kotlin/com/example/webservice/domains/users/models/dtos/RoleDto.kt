package com.example.webservice.domains.users.models.dtos

import com.example.webservice.domains.common.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class RoleDto : BaseDto() {
    @NotBlank
    lateinit var name: String

    @NotNull
    var restricted: Boolean = true

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var privileges: List<PrivilegeDto>? = null

    @NotNull
    @NotEmpty
    @JsonProperty(value = "privilege_ids", access = JsonProperty.Access.WRITE_ONLY)
    lateinit var privilegeIds: List<Long>


}
