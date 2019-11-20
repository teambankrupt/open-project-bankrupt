package com.example.webservice.domains.address.services

import com.example.webservice.domains.address.models.entities.Address
import com.example.webservice.domains.common.services.IDeleteService
import java.util.*

interface AddressService:IDeleteService {
    fun save(address: Address): Address
    fun find(id: Long): Optional<Address>
}
