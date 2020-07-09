package com.example.application.chat.services

import com.example.application.chat.models.entities.ChatRoom
import com.example.coreweb.domains.base.services.CrudService
import org.springframework.data.domain.Page

interface ChatRoomService : CrudService<ChatRoom> {
    fun search(query: String, username: String, page: Int, size: Int): Page<ChatRoom>
}