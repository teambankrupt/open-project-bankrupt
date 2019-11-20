package com.example.webservice.domains.address.services

import com.example.webservice.domains.address.models.entities.Village
import com.example.webservice.domains.common.services.IDeleteService
import org.springframework.data.domain.Page
import java.util.*

interface VillageService: IDeleteService {
    fun search(query: String, page: Int): Page<Village>
    fun find(id: Long): Optional<Village>
    fun save(village: Village): Village
}
