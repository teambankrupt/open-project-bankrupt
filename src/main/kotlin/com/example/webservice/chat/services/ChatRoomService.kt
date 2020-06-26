package com.example.webservice.chat.services

import com.example.webservice.chat.models.entities.ChatRoom
import com.example.webservice.domains.common.base.services.CrudService
import org.springframework.data.domain.Page

interface ChatRoomService : CrudService<ChatRoom> {
    fun search(query: String, username: String, page: Int, size: Int): Page<ChatRoom>
}