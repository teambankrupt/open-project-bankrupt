package com.example.webservice.domains.address.services

import com.example.webservice.domains.address.models.entities.Division
import com.example.webservice.domains.common.services.IDeleteService
import org.springframework.data.domain.Page
import java.util.*

interface DivisionService:IDeleteService {
    fun save(division: Division): Division
    fun search(query: String, page: Int): Page<Division>
    fun find(id: Long): Optional<Division>
}
