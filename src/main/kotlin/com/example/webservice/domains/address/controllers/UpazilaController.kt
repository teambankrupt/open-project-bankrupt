package com.example.webservice.domains.address.controllers

import com.example.webservice.domains.address.models.dto.UpazilaDto
import com.example.webservice.domains.address.models.entities.Upazila
import com.example.webservice.domains.address.models.mappers.UpazilaMapper
import com.example.webservice.domains.address.services.UpazilaService
import com.example.webservice.domains.common.controller.CrudParentController
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/upazilas")
class UpazilaController(
        @Autowired val upazilaService: UpazilaService,
        @Autowired val upazilaMapper: UpazilaMapper) : CrudParentController() {

    @GetMapping("")
    override fun search(@RequestParam("q", defaultValue = "") query: String, @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any>? {
        val upazilas: Page<Upazila> = upazilaService.search(query, page)
        return ResponseEntity.ok(upazilas.map { upazila -> upazilaMapper.map(upazila) })
    }

    @GetMapping("/{id}")
    override fun find(@PathVariable id: Long): ResponseEntity<Any>? {
        val upazila: Upazila = upazilaService.find(id).orElseThrow{ NotFoundException("Could not found upazila with id $id") }
        return ResponseEntity.ok(upazilaMapper.map(upazila))
    }

    @PostMapping("")
    fun create(@Valid @RequestBody upazilaDto: UpazilaDto): ResponseEntity<Any>? {
        var upazila = upazilaMapper.map(upazilaDto, null)
        upazila = upazilaService.save(upazila)
        return ResponseEntity.ok(upazilaMapper.map(upazila))
    }

    @PatchMapping("/{id}")
    fun update(@Valid @RequestBody upazilaDto: UpazilaDto, @PathVariable id: Long): ResponseEntity<Any>? {
        var upazila: Upazila = upazilaService.find(id).orElseThrow{ NotFoundException("Could not found upazila with id $id") }
        upazila = upazilaService.save(upazilaMapper.map(upazilaDto, upazila))
        return ResponseEntity.ok(upazilaMapper.map(upazila))
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Long): ResponseEntity<Any>? {
        this.upazilaService.softDelete(id)
        return ResponseEntity.ok().build()
    }
}
