package com.example.webservice.domains.common.controller

import com.example.webservice.domains.common.models.dtos.BaseDto
import org.springframework.http.ResponseEntity

interface CrudController<T : BaseDto> {
    fun search(query: String, page: Int): ResponseEntity<Any>
    fun find(id: Long): ResponseEntity<Any>
    fun create(dto: T): ResponseEntity<Any>
    fun update(id: Long, dto: T): ResponseEntity<Any>
    fun delete(id: Long): ResponseEntity<Any>
}