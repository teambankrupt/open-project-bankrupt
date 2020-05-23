package com.example.webservice.chat.models.entities

import com.example.webservice.domains.common.base.models.entities.BaseEntity
import com.example.webservice.domains.users.models.entities.User
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "chat_chatrooms")
class ChatRoom : BaseEntity() {
    lateinit var title: String

    @ManyToMany
    @JoinTable(name = "chat_chatroom_users")
    @LazyCollection(LazyCollectionOption.FALSE)
    lateinit var users: MutableList<User>
}