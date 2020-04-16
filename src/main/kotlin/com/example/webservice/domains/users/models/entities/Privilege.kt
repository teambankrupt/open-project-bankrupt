package com.example.webservice.domains.users.models.entities

import com.example.webservice.domains.common.models.entities.base.BaseEntity
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import java.util.*
import javax.persistence.*

import kotlin.collections.ArrayList

@Entity
@Table(name = "privileges")
class Privilege() : BaseEntity() {
    @Column(nullable = false, unique = true)
    lateinit var name: String

    @Column(nullable = false, unique = true)
    lateinit var label: String

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "privileges_access_urls")
    lateinit var accessUrls: List<String>

    fun accessesArr(): Array<String> {
        return accessUrls.toTypedArray()
    }

    constructor(name: String, label: String) : this() {
        this.name = name
        this.label = label
    }

    enum class Privileges(val label: String) {
        ADMINISTRATION("Administration"), ACCESS_USER_RESOURCES("Access User Resources")
    }

    public fun accessesStr(): String {
        return this.accessUrls.joinToString()
    }
}
