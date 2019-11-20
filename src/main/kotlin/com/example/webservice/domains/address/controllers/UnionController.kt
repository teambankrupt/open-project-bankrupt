package com.example.webservice.domains.address.controllers

import com.example.webservice.domains.address.models.dto.UnionDto
import com.example.webservice.domains.address.models.entities.Union
import com.example.webservice.domains.address.models.mappers.UnionMapper
import com.example.webservice.domains.address.services.UnionService
import com.example.webservice.domains.common.controller.CrudParentController
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/unions")
class UnionController @Autowired constructor(
        val unionService: UnionService,
        val unionMapper: UnionMapper) : CrudParentController() {

    @GetMapping("")
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any>? {
        val unions: Page<Union> = unionService.search(query, page)
        return ResponseEntity.ok(unions.map { union -> unionMapper.map(union) })
    }

    @GetMapping("/{id}")
    override fun find(@PathVariable id: Long): ResponseEntity<Any>? {
        val union: Union = unionService.find(id).orElseThrow{ NotFoundException("Could not find union with id $id") }
        return ResponseEntity.ok(unionMapper.map(union))
    }


    @PostMapping("")
    fun save(@Valid @RequestBody unionDto: UnionDto): ResponseEntity<Any>? {
        val union: Union = unionService.save(unionMapper.map(unionDto, null))
        return ResponseEntity.ok(unionMapper.map(union))
    }

    @PatchMapping("/{id}")
    fun update(@Valid @RequestBody unionDto: UnionDto, @PathVariable id: Long): ResponseEntity<Any>? {
        var union: Union = unionService.find(id).orElseThrow{ NotFoundException("Could not find union with id $id") }
        union = unionService.save(unionMapper.map(unionDto, union))
        return ResponseEntity.ok(unionMapper.map(union))
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Long): ResponseEntity<Any>? {
        this.unionService.softDelete(id)
        return ResponseEntity.ok().build()
    }
}
