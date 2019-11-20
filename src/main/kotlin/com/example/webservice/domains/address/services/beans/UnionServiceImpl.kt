package com.example.webservice.domains.address.services.beans

import com.example.webservice.commons.PageAttr
import com.example.webservice.domains.address.models.entities.Union
import com.example.webservice.domains.address.models.entities.Upazila
import com.example.webservice.domains.address.repositories.UnionRepo
import com.example.webservice.domains.address.services.UnionService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*


@Service
class UnionServiceImpl(@Autowired val unionRepo: UnionRepo) : UnionService {

    override fun save(union: Union): Union {
        return this.unionRepo.save(union)
    }

    override fun search(query: String, page: Int): Page<Union> {
        return this.unionRepo.search(query, PageAttr.getPageRequest(page))
    }

    override fun find(id: Long): Optional<Union> {
        return this.unionRepo.find(id)
    }

    override fun delete(id: Long) {
        //TODO not implemented yet
    }

    override fun softDelete(id: Long) {
        val union: Union = this.find(id).orElseThrow { NotFoundException("Could not find union with id $id") }
        union.isDeleted = true
        this.save(union)
    }

}
