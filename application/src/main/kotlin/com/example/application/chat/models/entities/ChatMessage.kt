package com.example.application.chat.models.entities

import com.example.application.domains.users.models.entities.User
import com.example.coreweb.domains.base.entities.BaseEntity
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