package com.example.acl.domains.users.controllers


import com.example.coreweb.commons.Constants
import com.example.coreweb.domains.base.controllers.CrudController
import com.example.acl.domains.users.models.dtos.RoleDto
import com.example.acl.domains.users.models.mappers.RoleMapper
import com.example.acl.domains.users.services.RoleService
import com.example.common.exceptions.notfound.NotFoundException
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/admin/roles")
@Api(tags = [Constants.Swagger.ROLES_ADMIN], description = Constants.Swagger.ROLES_ADMIN_API_DETAILS)
class RoleController @Autowired constructor(
        private val roleService: RoleService,
        private val roleMapper: RoleMapper
) : CrudController<RoleDto> {

    @GetMapping("")
    override fun search(@RequestParam("query", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<RoleDto>> {
        val roles = this.roleService.search(query, page, size)
        return ResponseEntity.ok(roles.map { this.roleMapper.map(it) })
    }


    @PostMapping("")
    override fun create(@Valid @RequestBody dto: RoleDto): ResponseEntity<RoleDto> {
        val role = this.roleService.save(this.roleMapper.map(dto, null))
        return ResponseEntity.ok(this.roleMapper.map(role))
    }

    @PatchMapping("/{id}")
    override fun update(@PathVariable id: Long,
                        @Valid @RequestBody dto: RoleDto): ResponseEntity<RoleDto> {
        val exRole = this.roleService.find(id).orElseThrow { NotFoundException("Could not find role with id: $id") }
        val role = this.roleService.save(this.roleMapper.map(dto, exRole))
        return ResponseEntity.ok(this.roleMapper.map(role))
    }

    @GetMapping("/{id}")
    override fun find(@PathVariable id: Long): ResponseEntity<RoleDto> {
        val role = this.roleService.find(id).orElseThrow { NotFoundException("Could not find role with id: $id") }
        return ResponseEntity.ok(this.roleMapper.map(role))
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.roleService.delete(id)
        return ResponseEntity.ok().build();
    }
}
