package com.example.coreweb.domains.address.services.beans

import com.example.coreweb.utils.PageAttr
import com.example.coreweb.domains.address.models.entities.Upazila
import com.example.coreweb.domains.address.repositories.UpazilaRepo
import com.example.coreweb.domains.address.services.UpazilaService
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*


@Service
class UpazilaServiceImpl @Autowired constructor(
        private val upazilaRepo: UpazilaRepo
) : UpazilaService {

    override fun save(entity: Upazila): Upazila {
        return upazilaRepo.save(entity)
    }

    override fun search(query: String, page: Int, size: Int): Page<Upazila> {
        return upazilaRepo.search(query, PageAttr.getPageRequest(page,size))
    }

    override fun find(id: Long): Optional<Upazila> {
        return upazilaRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.upazilaRepo.deleteById(id)
            return
        }
        val upazila: Upazila = find(id).orElseThrow { NotFoundException("Could not find upazila with id $id") }
        upazila.isDeleted = true
        save(upazila)
    }


}
