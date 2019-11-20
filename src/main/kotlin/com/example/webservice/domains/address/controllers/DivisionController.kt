package com.example.webservice.domains.address.controllers

import com.example.webservice.domains.address.models.dto.DivisionDto
import com.example.webservice.domains.address.models.entities.Division
import com.example.webservice.domains.address.models.mappers.DivisionMapper
import com.example.webservice.domains.address.services.DivisionService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/divisions")
class DivisionController(
        @Autowired val divisionService: DivisionService,
        @Autowired val divisionMapper: DivisionMapper
) {

    @GetMapping("")
    fun search(@RequestParam("q", defaultValue = "") query: String,
               @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any> {
        val divisions: Page<Division> = this.divisionService.search(query, page)
        return ResponseEntity.ok(divisions.map { division -> this.divisionMapper.map(division) })
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<Any> {
        val division: Division = this.divisionService.find(id).orElseThrow { NotFoundException("Could not found division with id $id") }
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @PostMapping("")
    fun save(@Valid @RequestBody divisionDto: DivisionDto): ResponseEntity<Any> {
        val division: Division = this.divisionService.save(this.divisionMapper.map(divisionDto, null))
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @PatchMapping("/{id}")
    fun update(@Valid @RequestBody divisionDto: DivisionDto, @PathVariable id: Long): ResponseEntity<Any> {
        var division: Division = this.divisionService.find(id).orElseThrow { NotFoundException("Could not found division with id $id") }
        division = this.divisionService.save(this.divisionMapper.map(divisionDto, division))
        return ResponseEntity.ok(this.divisionMapper.map(division))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any>? {
        this.divisionService.softDelete(id)
        return ResponseEntity.ok().build()
    }

}
