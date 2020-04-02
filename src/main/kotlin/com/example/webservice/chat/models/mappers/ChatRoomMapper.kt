package com.example.webservice.chat.models.mappers

import com.example.webservice.chat.models.dtos.ChatRoomDto
import com.example.webservice.chat.models.entities.ChatRoom
import com.example.webservice.commons.utils.ExceptionUtil
import com.example.webservice.domains.common.models.mappers.BaseMapper
import com.example.webservice.domains.users.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChatRoomMapper @Autowired constructor(
        private val userService: UserService
) : BaseMapper<ChatRoom, ChatRoomDto> {

    override fun map(entity: ChatRoom): ChatRoomDto {
        val dto = ChatRoomDto()
        dto.id = entity.id
        dto.created = entity.createdAt
        dto.updatedAt = entity.updatedAt

        dto.title = entity.title
        dto.users = entity.users.map { it.id }
        return dto
    }

    override fun map(dto: ChatRoomDto, exEntity: ChatRoom?): ChatRoom {
        val entity = exEntity ?: ChatRoom()
        entity.title = dto.title
        entity.users = dto.users
                .map { this.userService.find(it).orElseThrow { ExceptionUtil.getNotFound("user", it) } }
                .toMutableList()
        return entity
    }

}