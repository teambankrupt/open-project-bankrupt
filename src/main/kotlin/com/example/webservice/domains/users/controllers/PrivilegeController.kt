package com.example.webservice.domains.users.controllers

import com.example.webservice.commons.Constants
import com.example.webservice.domains.users.models.dtos.PrivilegeDto
import com.example.webservice.domains.users.models.mappers.PrivilegeMapper
import com.example.webservice.domains.users.services.PrivilegeService
import com.example.webservice.exceptions.notfound.NotFoundException
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin/privileges")
@Api(tags = [Constants.Swagger.PRIVILEGES_ADMIN], description = Constants.Swagger.PRIVILEGES_ADMIN_API_DETAILS)
class PrivilegeController @Autowired constructor(
        private val privilegeService: PrivilegeService,
        private val privilegeMapper: PrivilegeMapper
) {

    @PostMapping("")
    fun create(@RequestBody dto: PrivilegeDto): ResponseEntity<Any> {
        val privilege = this.privilegeService.save(this.privilegeMapper.map(dto, null))
        return ResponseEntity.ok(this.privilegeMapper.map(privilege))
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody dto: PrivilegeDto): ResponseEntity<Any> {
        var privilege = this.privilegeService.find(id).orElseThrow { NotFoundException("Could not find privilege with id: $id") }
        privilege = this.privilegeService.save(this.privilegeMapper.map(dto, privilege))
        return ResponseEntity.ok(this.privilegeMapper.map(privilege))
    }

    @GetMapping("")
    fun search(@RequestParam("query", defaultValue = "") query: String): ResponseEntity<Any> {
        val privileges = this.privilegeService.search(query)
        return ResponseEntity.ok(privileges.map { privilege -> this.privilegeMapper.map(privilege) })
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<Any> {
        val privilege = this.privilegeService.find(id).orElseThrow { NotFoundException("Couldn't find privilege with id: $id") }
        return ResponseEntity.ok(this.privilegeMapper.map(privilege))
    }

    @DeleteMapping("/{id}")
    fun softDelete(@PathVariable id: Long): ResponseEntity<Any> {
        this.privilegeService.delete(id)
        return ResponseEntity.ok().build()
    }

}
