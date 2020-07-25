package com.example.coreweb.domains.address.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "addr_divisions")
class Division : BaseEntity() {
    @Column(name = "name_en")
    var nameEn: String? = null

    @Column(name = "name_bn")
    var nameBn: String? = null
}
