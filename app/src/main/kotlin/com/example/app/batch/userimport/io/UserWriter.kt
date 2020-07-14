package com.example.app.batch.userimport.io

import com.example.auth.entities.User
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class UserWriter : ItemWriter<User> {

    override fun write(users: MutableList<out User>) {
        users.forEach { println(it.name) }
    }
}
