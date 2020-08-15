package com.example.app.domains.crudexample.services.beans

import com.example.app.domains.crudexample.models.entities.CrudExample
import com.example.app.domains.crudexample.repositories.CrudExampleRepository
import com.example.app.domains.crudexample.services.CrudExampleService
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class CrudExampleServiceBean @Autowired constructor(
        private val crudExampleRepository: CrudExampleRepository
) : CrudExampleService {

    override fun search(query: String, page: Int, size: Int): Page<CrudExample> {
        return this.crudExampleRepository.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun save(entity: CrudExample): CrudExample {
        return this.crudExampleRepository.save(entity)
    }

    override fun find(id: Long): Optional<CrudExample> {
        return this.crudExampleRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("CrudExample", id) }
            entity.isDeleted = true
            this.crudExampleRepository.save(entity)
        }
        this.crudExampleRepository.deleteById(id)
    }

}