package com.example.webservice.domains.address.services

import com.example.webservice.domains.address.models.entities.Union
import com.example.webservice.domains.common.services.IDeleteService
import org.jetbrains.annotations.NotNull
import org.springframework.data.domain.Page
import java.util.*

interface UnionService:IDeleteService {
    fun save(@NotNull union: Union): Union
    fun search(query: String, page: Int): Page<Union>
    fun find(@NotNull id: Long): Optional<Union>
}
