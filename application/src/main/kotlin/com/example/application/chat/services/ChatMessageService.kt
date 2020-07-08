package com.example.application.chat.services

import com.example.application.chat.models.entities.ChatMessage
import com.example.application.domains.common.base.services.CrudService

interface ChatMessageService : CrudService<ChatMessage> {
}