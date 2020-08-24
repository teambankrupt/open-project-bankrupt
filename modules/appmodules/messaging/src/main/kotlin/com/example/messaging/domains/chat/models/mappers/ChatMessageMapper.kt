package com.example.messaging.domains.chat.models.mappers

import com.example.auth.services.AuthService
import com.example.common.utils.ExceptionUtil
import com.example.messaging.domains.chat.models.dtos.ChatMessageDto
import com.example.messaging.domains.chat.models.entities.ChatMessage
import com.example.messaging.domains.chat.services.ChatRoomService
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat

@Component
class ChatMessageMapper @Autowired constructor(
        private val chatRoomService: ChatRoomService,
        private val authService: AuthService
) : BaseMapper<ChatMessage, ChatMessageDto> {

    override fun map(entity: ChatMessage): ChatMessageDto {
        val dto = ChatMessageDto()
        dto.id = entity.id
        dto.createdAt = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.from = entity.from
        dto.chatRoomId = entity.chatRoom.id
        dto.content = entity.content
        dto.time = SimpleDateFormat("HH:mm").format(entity.updatedAt)
        return dto
    }

    override fun map(dto: ChatMessageDto, exEntity: ChatMessage?): ChatMessage {
        val entity = exEntity ?: ChatMessage()

        entity.chatRoom = this.chatRoomService.find(dto.chatRoomId).orElseThrow {  ExceptionUtil.notFound("Could not find chatroom with id: ${dto.chatRoomId}")  }
        entity.from = if (this.authService.existsByUsername(dto.from
                        ?: "")) dto.from.toString() else throw  ExceptionUtil.notFound("Could not find user with username: ${dto.from}")
        entity.content = dto.content
        return entity
    }


}