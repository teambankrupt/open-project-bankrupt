package com.example.webservice.domains.address.controllers

import com.example.webservice.domains.address.models.dto.DistrictDto
import com.example.webservice.domains.address.models.entities.District
import com.example.webservice.domains.address.models.mappers.DistrictMapper
import com.example.webservice.domains.address.services.DistrictService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/districts")
class DistrictController(
        @Autowired val districtService: DistrictService,
        @Autowired val districtMapper: DistrictMapper
) {

    @GetMapping("")
    fun search(@RequestParam("q", defaultValue = "") query: String,
               @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any> {
        val districts: Page<District> = this.districtService.search(query, page)
        return ResponseEntity.ok(districts.map { district -> this.districtMapper.map(district) })
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<Any> {
        val district: District = this.districtService.find(id).orElseThrow{ NotFoundException("Could not found district with id $id")}
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @PostMapping("")
    fun save(@Valid @RequestBody districtDto: DistrictDto): ResponseEntity<Any> {
        val district: District = this.districtService.save(this.districtMapper.map(districtDto, null))
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @PatchMapping("/{id}")
    fun update(@Valid @RequestBody districtDto: DistrictDto, @PathVariable id: Long): ResponseEntity<Any> {
        var district: District = this.districtService.find(id).orElseThrow{ NotFoundException("Could not found district with id $id")}
        district = this.districtService.save(this.districtMapper.map(districtDto, district))
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any>? {
        this.districtService.softDelete(id)
        return ResponseEntity.ok().build()
    }

}
