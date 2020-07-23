package com.example.coreweb.domains.base.controllers

import com.example.coreweb.domains.base.models.dtos.BaseDto
import org.springframework.ui.Model

interface CrudWebController<T : BaseDto> {
    fun search(query: String, page: Int, size: Int, model: Model): String
    fun find(id: Long): String
    fun createPage(model: Model): String
    fun create(dto: T): String
    fun updatePage(id: Long, model: Model): String
    fun update(id: Long, dto: T): String
    fun delete(id: Long): String
}