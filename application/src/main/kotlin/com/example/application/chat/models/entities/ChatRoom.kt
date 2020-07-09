package com.example.application.chat.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import com.example.application.domains.users.models.entities.User
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