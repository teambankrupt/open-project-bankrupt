package com.example.messaging.domains.chat.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "chat_messages")
class ChatMessage : BaseEntity() {

    @Column(name = "from_username")
    lateinit var from: String

    @ManyToOne
    @JoinColumn(nullable = false)
    lateinit var chatRoom: ChatRoom

    lateinit var content: String


}