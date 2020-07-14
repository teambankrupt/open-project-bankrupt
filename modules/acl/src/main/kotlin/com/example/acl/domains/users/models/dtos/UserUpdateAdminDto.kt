package com.example.acl.domains.users.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

class UserUpdateAdminDto : BaseDto() {
    @NotBlank
    lateinit var name: String

    @NotBlank
    @Size(min = 3)
    lateinit var username: String

    var phone: String? = null

    @Email
    var email: String? = null

    lateinit var password: String

    @NotBlank
    lateinit var gender: String

    @NotEmpty
    lateinit var roleIds: List<Long>

    var enabled = false

    var accountNonExpired = false

    var accountNonLocked = false

    var credentialsNonExpired = false
}