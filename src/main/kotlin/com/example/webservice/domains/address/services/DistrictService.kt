package com.example.webservice.domains.address.services

import com.example.webservice.domains.address.models.entities.District
import com.example.webservice.domains.common.services.IDeleteService
import org.jetbrains.annotations.NotNull
import org.springframework.data.domain.Page
import java.util.*

interface DistrictService: IDeleteService {
    fun save(@NotNull district: District): District
    fun search(query: String, page: Int): Page<District>
    fun find(@NotNull id: Long): Optional<District>
}
