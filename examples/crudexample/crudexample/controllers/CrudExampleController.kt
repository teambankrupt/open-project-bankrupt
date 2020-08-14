package com.example.app.domains.crudexample.controllers

import com.example.app.domains.crudexample.models.dtos.CrudExampleDto
import com.example.app.domains.crudexample.models.mappers.CrudExampleMapper
import com.example.app.domains.crudexample.services.CrudExampleService
import com.example.app.routing.Route
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudController
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = ["CrudExamples"], description = "Description about CrudExamples")
class CrudExampleController @Autowired constructor(
        private val crudExampleService: CrudExampleService,
        private val crudExampleMapper: CrudExampleMapper
) : CrudController<CrudExampleDto> {

    /*
        COPY THESE URLS TO ROUTE FILE AND ADJUST
        ------------------------------------------------------
        const val SEARCH_EXAMPLES = "$API$VERSION/crudexamples"
        const val CREATE_EXAMPLE = "$API$VERSION/crudexamples"
        const val FIND_EXAMPLE = "$API$VERSION/crudexamples/{id}"
        const val UPDATE_EXAMPLE = "$API$VERSION/crudexamples/{id}"
        const val DELETE_EXAMPLE = "$API$VERSION/crudexamples/{id}"
    */

    @GetMapping(Route.V1.SEARCH_EXAMPLES)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<CrudExampleDto>> {
        val examples = this.crudExampleService.search(query, page, size);
        return ResponseEntity.ok(examples.map { this.crudExampleMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_EXAMPLE)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<CrudExampleDto> {
        val example = this.crudExampleService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        return ResponseEntity.ok(this.crudExampleMapper.map(example))
    }

    @PostMapping(Route.V1.CREATE_EXAMPLE)
    override fun create(@Valid @RequestBody dto: CrudExampleDto): ResponseEntity<CrudExampleDto> {
        val example = this.crudExampleService.save(this.crudExampleMapper.map(dto, null))
        return ResponseEntity.ok(this.crudExampleMapper.map(example))
    }

    @PatchMapping(Route.V1.UPDATE_EXAMPLE)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: CrudExampleDto): ResponseEntity<CrudExampleDto> {
        var example = this.crudExampleService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        example = this.crudExampleMapper.map(dto, example)
        return ResponseEntity.ok(this.crudExampleMapper.map(example))
    }

    @DeleteMapping(Route.V1.DELETE_EXAMPLE)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.crudExampleService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}