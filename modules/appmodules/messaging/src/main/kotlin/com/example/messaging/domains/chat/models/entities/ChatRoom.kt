package com.example.messaging.domains.chat.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "chat_chatrooms")
class ChatRoom : BaseEntity() {
    lateinit var title: String

    @ElementCollection
    @JoinTable(name = "chat_chatroom_users")
    @LazyCollection(LazyCollectionOption.FALSE)
    lateinit var users: MutableList<String>
}