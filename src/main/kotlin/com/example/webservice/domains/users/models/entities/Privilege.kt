package com.example.webservice.domains.users.models.entities

import com.example.webservice.domains.common.models.entities.base.BaseEntity
import javax.persistence.Column

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "privileges")
class Privilege() : BaseEntity() {
    @Column(nullable = false)
    lateinit var name: String
    @Column(nullable = false)
    lateinit var label: String

    constructor(name: String, label: String) : this() {
        this.name = name
        this.label = label
    }

    enum class Privileges(val label: String) {
        ADMINISTRATION("Administration"), ACCESS_USER_RESOURCES("Access User Resources")
    }
}
