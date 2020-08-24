package com.example.acl.domains.users.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserRequest : BaseDto() {
    @NotBlank
    lateinit var name: String

    @NotBlank
    @Size(min = 3)
    lateinit var username: String

    var phone: String? = null

    @Email
    var email: String? = null

    @NotBlank
    @Size(min = 6)
    lateinit var password: String

    @NotBlank
    lateinit var gender: String

    @NotBlank
    lateinit var role: String
}
