package com.example.virtualfilesystem.domains.vextensions.controllers

import com.example.virtualfilesystem.domains.vextensions.models.dtos.VExtensionDto
import com.example.virtualfilesystem.domains.vextensions.models.mappers.VExtensionMapper
import com.example.virtualfilesystem.domains.vextensions.services.VExtensionService
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudController
import com.example.virtualfilesystem.domains.vfiles.models.VFile
import com.example.virtualfilesystem.routing.Route
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = ["VExtensions"], description = "Description about VExtensions")
class VExtensionController @Autowired constructor(
        private val vExtensionService: VExtensionService,
        private val vExtensionMapper: VExtensionMapper
) : CrudController<VExtensionDto> {

    @GetMapping(Route.V1.SEARCH_VEXTENSIONS)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<VExtensionDto>> {
        val entities = this.vExtensionService.search(query, page, size)
        return ResponseEntity.ok(entities.map { this.vExtensionMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_VEXTENSION)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<VExtensionDto> {
        val entity = this.vExtensionService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        return ResponseEntity.ok(this.vExtensionMapper.map(entity))
    }

    @PostMapping(Route.V1.CREATE_VEXTENSION)
    override fun create(@Valid @RequestBody dto: VExtensionDto): ResponseEntity<VExtensionDto> {
        val entity = this.vExtensionService.save(this.vExtensionMapper.map(dto, null))
        return ResponseEntity.ok(this.vExtensionMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_VEXTENSION)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: VExtensionDto): ResponseEntity<VExtensionDto> {
        var entity = this.vExtensionService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        entity = this.vExtensionService.save(this.vExtensionMapper.map(dto, entity))
        return ResponseEntity.ok(this.vExtensionMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_VEXTENSION)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.vExtensionService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}