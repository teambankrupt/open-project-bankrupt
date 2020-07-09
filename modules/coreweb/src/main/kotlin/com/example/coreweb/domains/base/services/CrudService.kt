package com.example.coreweb.domains.base.services

import com.example.coreweb.domains.base.entities.BaseEntity
import org.springframework.data.domain.Page
import java.util.*

interface CrudService<T : BaseEntity> {
    fun search(query: String, page: Int, size: Int): Page<T>
    fun save(entity: T): T
    fun find(id: Long): Optional<T>
    fun delete(id: Long, softDelete: Boolean)
}