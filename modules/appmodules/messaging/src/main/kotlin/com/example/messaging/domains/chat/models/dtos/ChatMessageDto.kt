package com.example.messaging.domains.chat.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ChatMessageDto : BaseDto() {

    @NotNull
    @JsonProperty("chat_room_id")
    var chatRoomId: Long = 0

    @NotBlank
    lateinit var content: String

    var from: String? = null
        @JsonIgnore set
        @JsonProperty("from") get

    var time: String? = null
        @JsonIgnore set
        @JsonProperty("time") get
}