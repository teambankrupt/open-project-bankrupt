package com.example.webservice.domains.address.controllers

import com.example.webservice.domains.address.models.dto.AddressDto
import com.example.webservice.domains.address.models.entities.Address
import com.example.webservice.domains.address.models.mappers.AddressMapper
import com.example.webservice.domains.address.services.AddressService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/addresses")
class AddressController @Autowired constructor (
        val addressService: AddressService,
        val addressMapper: AddressMapper ) {

    @PostMapping("")
    fun create(@Valid @RequestBody addressDto: AddressDto) : ResponseEntity<Any> {
        val address: Address = this.addressService.save(this.addressMapper.map(addressDto, null))
        return ResponseEntity.ok(this.addressMapper.map(address))
    }

    @PatchMapping("/{id}")
    fun update(@Valid @RequestBody addressDto: AddressDto, @PathVariable id: Long): ResponseEntity<Any>? {
        var address: Address = addressService.find(id).orElseThrow { NotFoundException("Could not found address with id $id") }
        address = addressService.save(addressMapper.map(addressDto, address))
        return ResponseEntity.ok(addressMapper.map(address))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any>? {
        this.addressService.softDelete(id)
        return ResponseEntity.ok().build()
    }
}
