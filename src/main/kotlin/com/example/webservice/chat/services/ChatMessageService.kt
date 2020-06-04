package com.example.webservice.chat.services

import com.example.webservice.chat.models.entities.ChatMessage
import com.example.webservice.domains.common.base.services.CrudService

interface ChatMessageService : CrudService<ChatMessage> {
}