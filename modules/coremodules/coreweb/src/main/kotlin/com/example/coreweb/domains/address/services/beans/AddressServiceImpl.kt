package com.example.coreweb.domains.address.services.beans

import com.example.coreweb.utils.PageAttr
import com.example.coreweb.domains.address.models.entities.Address
import com.example.coreweb.domains.address.repositories.AddressRepo
import com.example.coreweb.domains.address.services.AddressService
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class AddressServiceImpl @Autowired constructor(
        private val addressRepo: AddressRepo
) : AddressService {

    override fun search(query: String, page: Int, size: Int): Page<Address> {
        return this.addressRepo.search(query, PageAttr.getPageRequest(page,size))
    }

    override fun save(entity: Address): Address {
        return this.addressRepo.save(entity)
    }

    override fun find(id: Long): Optional<Address> {
        return this.addressRepo.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (!softDelete) {
            this.addressRepo.deleteById(id)
            return
        }
        val addr: Address = this.find(id).orElseThrow { NotFoundException("Could not find address with id $id") }
        addr.isDeleted = true
        this.save(addr)
    }

}
