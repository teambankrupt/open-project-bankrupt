package com.example.webservice.domains.address.services

import com.example.webservice.domains.address.models.entities.Upazila
import com.example.webservice.domains.common.services.IDeleteService
import org.springframework.data.domain.Page
import java.util.*

interface UpazilaService :IDeleteService{
    fun save(upazilaEntity: Upazila): Upazila
    fun search(query: String, page: Int): Page<Upazila>
    fun find(id: Long): Optional<Upazila>
}
