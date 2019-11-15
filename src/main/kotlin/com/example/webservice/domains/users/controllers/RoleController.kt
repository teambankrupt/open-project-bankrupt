package com.example.webservice.domains.users.controllers


import com.example.webservice.domains.users.models.dtos.RoleDto
import com.example.webservice.domains.users.models.mappers.RoleMapper
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin/roles")
class RoleController @Autowired constructor(
        val roleService: RoleService,
        val roleMapper: RoleMapper
) {

    @GetMapping("")
    fun getAll(@RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any> {
        val roles = this.roleService.findAll(page)
        return ResponseEntity.ok(roles.map { this.roleMapper.map(it) })
    }


    @PostMapping("")
    fun create(@RequestBody dto: RoleDto): ResponseEntity<Any> {
        val role = this.roleService.save(this.roleMapper.map(dto, null))
        return ResponseEntity.ok(this.roleMapper.map(role))
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody dto: RoleDto): ResponseEntity<Any> {
        val exRole = this.roleService.find(id).orElseThrow { NotFoundException("Could not find role with id: $id") }
        val role = this.roleService.save(this.roleMapper.map(dto, exRole))
        return ResponseEntity.ok(this.roleMapper.map(role))
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<Any> {
        val role = this.roleService.find(id).orElseThrow { NotFoundException("Could not find role with id: $id") }
        return ResponseEntity.ok(this.roleMapper.map(role))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.roleService.delete(id)
        return ResponseEntity.ok().build();
    }
}
