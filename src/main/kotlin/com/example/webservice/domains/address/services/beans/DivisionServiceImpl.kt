package com.example.webservice.domains.address.services.beans

import com.example.webservice.commons.PageAttr
import com.example.webservice.domains.address.models.entities.Division
import com.example.webservice.domains.address.repositories.DivisionRepo
import com.example.webservice.domains.address.services.DivisionService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class DivisionServiceImpl(@Autowired val divisionRepo: DivisionRepo) : DivisionService {

    override fun save(division: Division): Division {
        return this.divisionRepo.save(division)
    }

    override fun search(query: String, page: Int): Page<Division> {
        return this.divisionRepo.search(query, PageAttr.getPageRequest(page))
    }

    override fun find(id: Long): Optional<Division> {
        return this.divisionRepo.find(id)
    }

    override fun delete(id: Long) {
        //TODO not implemented yet
    }

    override fun softDelete(id: Long) {
        val division: Division = this.find(id).orElseThrow{ NotFoundException("Could not find division with id $id" ) }
        division.isDeleted = true
        this.save(division)
    }

}
