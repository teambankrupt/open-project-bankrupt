package com.example.app.domains.crudexample.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "crudexamples")
class CrudExample : BaseEntity() {
}