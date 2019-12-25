package com.example.webservice.domains.address.controllers

import com.example.webservice.commons.Constants
import com.example.webservice.commons.utils.ExceptionUtil
import com.example.webservice.domains.address.models.dto.UnionDto
import com.example.webservice.domains.address.models.entities.Union
import com.example.webservice.domains.address.models.mappers.UnionMapper
import com.example.webservice.domains.address.services.UnionService
import com.example.webservice.domains.common.controller.CrudController
import com.example.webservice.routing.Route
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = [Constants.Swagger.UNION], description = Constants.Swagger.REST_API)
class UnionController @Autowired constructor(
        private val unionService: UnionService,
        private val unionMapper: UnionMapper) : CrudController<UnionDto> {

    @GetMapping(Route.V1.SEARCH_UNION)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.UNION)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int): ResponseEntity<Any> {
        val unions: Page<Union> = unionService.search(query, page)
        return ResponseEntity.ok(unions.map { union -> unionMapper.map(union) })
    }

    @GetMapping(Route.V1.FIND_UNION)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.UNION)
    override fun find(@PathVariable id: Long): ResponseEntity<Any> {
        val union: Union = unionService.find(id).orElseThrow { ExceptionUtil.getNotFound("union", id) }
        return ResponseEntity.ok(unionMapper.map(union))
    }


    @PostMapping(Route.V1.CREATE_UNION)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.UNION)
    override fun create(@Valid @RequestBody dto: UnionDto): ResponseEntity<Any> {
        val union: Union = unionService.save(unionMapper.map(dto, null))
        return ResponseEntity.ok(unionMapper.map(union))
    }

    @PatchMapping(Route.V1.UPDATE_UNION)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.UNION)
    override fun update(@PathVariable id: Long, @Valid @RequestBody dto: UnionDto): ResponseEntity<Any> {
        var union: Union = unionService.find(id).orElseThrow { ExceptionUtil.getNotFound("union", id) }
        union = unionService.save(unionMapper.map(dto, union))
        return ResponseEntity.ok(unionMapper.map(union))
    }

    @DeleteMapping(Route.V1.DELETE_UNION)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.UNION)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.unionService.delete(id, true)
        return ResponseEntity.ok().build()
    }
}
