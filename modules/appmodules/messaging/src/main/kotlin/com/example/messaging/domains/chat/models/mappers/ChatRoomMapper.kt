package com.example.messaging.domains.chat.models.mappers

import com.example.auth.services.AuthService
import com.example.common.utils.ExceptionUtil
import com.example.messaging.domains.chat.models.dtos.ChatRoomDto
import com.example.messaging.domains.chat.models.entities.ChatRoom
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChatRoomMapper @Autowired constructor(
        private val authService: AuthService
) : BaseMapper<ChatRoom, ChatRoomDto> {

    override fun map(entity: ChatRoom): ChatRoomDto {
        val dto = ChatRoomDto()
        dto.id = entity.id
        dto.createdAt = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.title = entity.title
        dto.users = entity.users
        return dto
    }

    override fun map(dto: ChatRoomDto, exEntity: ChatRoom?): ChatRoom {
        val entity = exEntity ?: ChatRoom()
        entity.title = dto.title
        entity.users = dto.users
                .map { if (this.authService.existsByUsername(it)) it else throw  ExceptionUtil.notFound("Could not find user with username: $it")  }
                .toMutableList()
        return entity
    }

}