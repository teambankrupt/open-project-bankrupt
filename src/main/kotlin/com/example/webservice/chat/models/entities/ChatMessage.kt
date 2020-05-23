package com.example.webservice.chat.models.entities

import com.example.webservice.domains.common.base.models.entities.BaseEntity
import com.example.webservice.domains.users.models.entities.User
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "chat_mesaages")
class ChatMessage : BaseEntity() {
    @ManyToOne
    lateinit var from: User

    @ManyToOne
    @JoinColumn(nullable = false)
    lateinit var chatRoom: ChatRoom

    lateinit var content: String


}