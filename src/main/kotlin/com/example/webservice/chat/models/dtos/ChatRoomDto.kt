package com.example.webservice.chat.models.dtos

import com.example.webservice.domains.common.base.models.dtos.BaseDto
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ChatRoomDto : BaseDto() {
    @NotNull
    lateinit var title: String

    @NotNull
    @NotEmpty
    lateinit var users: List<Long>
}