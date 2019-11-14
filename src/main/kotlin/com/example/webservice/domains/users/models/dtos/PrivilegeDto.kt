package com.example.webservice.domains.users.models.dtos

import com.example.webservice.domains.common.models.dtos.BaseDto

class PrivilegeDto : BaseDto() {
    lateinit var name: String
    lateinit var label: String
}
