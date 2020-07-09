package com.example.application.chat.services

import com.example.application.chat.models.entities.ChatMessage
import com.example.coreweb.domains.base.services.CrudService

interface ChatMessageService : CrudService<ChatMessage> {
}