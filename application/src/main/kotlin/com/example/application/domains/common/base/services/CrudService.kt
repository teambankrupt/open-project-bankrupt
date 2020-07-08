package com.example.application.domains.common.base.services

import com.example.application.domains.common.base.models.entities.BaseEntity
import org.springframework.data.domain.Page
import java.util.*

interface CrudService<T : BaseEntity> {
    fun search(query: String, page: Int, size: Int): Page<T>
    fun save(entity: T): T
    fun find(id: Long): Optional<T>
    fun delete(id: Long, softDelete: Boolean)
}