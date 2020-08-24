package com.example.coreweb.domains.base.controllers

import com.example.coreweb.domains.base.models.dtos.BaseDto
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity

interface CrudController<T : BaseDto> {
    fun search(query: String, page: Int, size: Int): ResponseEntity<Page<T>>
    fun find(id: Long): ResponseEntity<T>
    fun create(dto: T): ResponseEntity<T>
    fun update(id: Long, dto: T): ResponseEntity<T>
    fun delete(id: Long): ResponseEntity<Any>
}