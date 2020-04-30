package com.example.webservice.chat.services.beans

import com.example.webservice.chat.models.entities.ChatMessage
import com.example.webservice.chat.repositories.ChatMessageRepository
import com.example.webservice.chat.services.ChatMessageService
import com.example.webservice.commons.PageAttr
import com.example.webservice.commons.utils.ExceptionUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatMessageServiceBean @Autowired constructor(
        private val chatMessageRepository: ChatMessageRepository
) : ChatMessageService {
    override fun search(query: String, page: Int, size: Int): Page<ChatMessage> {
        return this.chatMessageRepository.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun save(entity: ChatMessage): ChatMessage {
        return this.chatMessageRepository.save(entity)
    }

    override fun find(id: Long): Optional<ChatMessage> {
        return this.chatMessageRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val cm = this.chatMessageRepository.find(id).orElseThrow { ExceptionUtil.getNotFound("Chat Message", id) }
            cm.isDeleted = false
            this.save(cm)
            return
        }
        this.chatMessageRepository.deleteById(id)
    }

}