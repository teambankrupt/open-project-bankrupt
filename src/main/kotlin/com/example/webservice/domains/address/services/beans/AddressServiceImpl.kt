package com.example.webservice.domains.address.services.beans

import com.example.webservice.domains.address.models.entities.Address
import com.example.webservice.domains.address.repositories.AddressRepo
import com.example.webservice.domains.address.services.AddressService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AddressServiceImpl @Autowired constructor(val addressRepo: AddressRepo) : AddressService {
    override fun save(address: Address): Address {
        return this.addressRepo.save(address)
    }

    override fun find(id: Long): Optional<Address> {
        return this.addressRepo.find(id)
    }

    override fun delete(id: Long) {
        //TODO not implemented yet
    }

    override fun softDelete(id: Long) {
        val addr: Address = this.find(id).orElseThrow { NotFoundException("Could not find address with id $id") }
        addr.isDeleted = true
        this.save(addr)
    }

}
