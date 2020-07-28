package com.example.coreweb.domains.address.services.beans

import com.example.coreweb.utils.PageAttr
import com.example.coreweb.domains.address.models.entities.Division
import com.example.coreweb.domains.address.repositories.DivisionRepo
import com.example.coreweb.domains.address.services.DivisionService
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class DivisionServiceImpl @Autowired constructor(
        private val divisionRepo: DivisionRepo
) : DivisionService {

    override fun save(entity: Division): Division {
        return this.divisionRepo.save(entity)
    }

    override fun search(query: String, page: Int, size: Int): Page<Division> {
        return this.divisionRepo.search(query, PageAttr.getPageRequest(page,size))
    }

    override fun find(id: Long): Optional<Division> {
        return this.divisionRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.divisionRepo.deleteById(id)
            return
        }
        val division: Division = this.find(id).orElseThrow { NotFoundException("Could not find division with id $id") }
        division.isDeleted = true
        this.save(division)
    }

}
