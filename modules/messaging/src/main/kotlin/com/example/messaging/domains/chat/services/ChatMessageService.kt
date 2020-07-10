package com.example.messaging.domains.chat.services

import com.example.messaging.domains.chat.models.entities.ChatMessage
import com.example.coreweb.domains.base.services.CrudService

interface ChatMessageService : CrudService<ChatMessage> {
}