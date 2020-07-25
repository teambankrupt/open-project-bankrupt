package com.example.coreweb.domains.address.services.beans

import com.example.coreweb.utils.PageAttr
import com.example.coreweb.domains.address.models.entities.Union
import com.example.coreweb.domains.address.repositories.UnionRepo
import com.example.coreweb.domains.address.services.UnionService
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*


@Service
class UnionServiceImpl @Autowired constructor(
        private val unionRepo: UnionRepo
) : UnionService {

    override fun save(entity: Union): Union {
        return this.unionRepo.save(entity)
    }

    override fun search(query: String, page: Int, size: Int): Page<Union> {
        return this.unionRepo.search(query, PageAttr.getPageRequest(page,size))
    }

    override fun find(id: Long): Optional<Union> {
        return this.unionRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.unionRepo.deleteById(id)
            return
        }
        val union: Union = this.find(id).orElseThrow { NotFoundException("Could not find union with id $id") }
        union.isDeleted = true
        this.save(union)
    }


}
