package com.example.application.batch.userimport.io

import com.example.application.domains.users.models.entities.User
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class UserWriter : ItemWriter<User> {

    override fun write(users: MutableList<out User>) {
        users.forEach { println(it.name) }
    }
}
