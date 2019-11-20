package com.example.webservice.domains.address.controllers

import com.example.webservice.domains.address.models.dto.VillageDto
import com.example.webservice.domains.address.models.entities.Village
import com.example.webservice.domains.address.models.mappers.VillageMapper
import com.example.webservice.domains.address.services.VillageService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/villages")
class VillageController @Autowired constructor(val villageService: VillageService, val villageMapper: VillageMapper) {

    @GetMapping("")
    fun search(@RequestParam("q", defaultValue = "") query: String,
               @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any> {
        val villages: Page<Village> = this.villageService.search(query, page)
        return ResponseEntity.ok(villages.map { village -> this.villageMapper.map(village) })
    }

    @PostMapping("")
    fun create(@Valid @RequestBody villageDto: VillageDto): ResponseEntity<Any> {
        val village: Village = this.villageService.save(this.villageMapper.map(villageDto, null))
        return ResponseEntity.ok(this.villageMapper.map(village))
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<Any>? {
        val village: Village = this.villageService.find(id).orElseThrow { NotFoundException("Could not found village with id $id") }
        return ResponseEntity.ok(this.villageMapper.map(village))
    }

    @PatchMapping("/{id}")
    fun update(@Valid @RequestBody villageDto: VillageDto, @PathVariable id: Long): ResponseEntity<Any>? {
        var village: Village = villageService.find(id).orElseThrow { NotFoundException("Could not found village with id $id") }
        village = villageService.save(villageMapper.map(villageDto, village))
        return ResponseEntity.ok(villageMapper.map(village))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any>? {
        this.villageService.softDelete(id)
        return ResponseEntity.ok().build()
    }
}
