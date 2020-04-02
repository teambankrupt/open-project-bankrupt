package com.example.webservice.chat.models.entities

import com.example.webservice.domains.common.models.entities.base.BaseEntity
import com.example.webservice.domains.users.models.entities.User
import javax.persistence.*

@Entity
@Table(name = "chat_chatrooms")
class ChatRoom : BaseEntity() {
    lateinit var title: String

    @ElementCollection
    @JoinTable(name = "chat_chatroom_users")
    lateinit var users: MutableList<User>
}