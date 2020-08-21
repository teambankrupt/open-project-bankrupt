package com.example.app.domains.issues.controllers

import com.example.app.domains.issues.models.dtos.IssueDto
import com.example.app.domains.issues.models.mappers.IssueMapper
import com.example.app.domains.issues.services.IssueService
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
@Api(tags = ["Issues"], description = "Description about Issues")
class IssueController @Autowired constructor(
        private val issueService: IssueService,
        private val issueMapper: IssueMapper
) : CrudController<IssueDto> {

    /*
        COPY THESE URLS TO ROUTE FILE AND ADJUST
        ------------------------------------------------------
        const val SEARCH_ISSUES = "$API$VERSION/issues"
        const val CREATE_ISSUE = "$API$VERSION/issues"
        const val FIND_ISSUE = "$API$VERSION/issues/{id}"
        const val UPDATE_ISSUE = "$API$VERSION/issues/{id}"
        const val DELETE_ISSUE = "$API$VERSION/issues/{id}"
    */

    @GetMapping(Route.V1.SEARCH_ISSUES)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<IssueDto>> {
        val entities = this.issueService.search(query, page, size);
        return ResponseEntity.ok(entities.map { this.issueMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_ISSUE)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<IssueDto> {
        val entity = this.issueService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        return ResponseEntity.ok(this.issueMapper.map(entity))
    }

    @PostMapping(Route.V1.CREATE_ISSUE)
    override fun create(@Valid @RequestBody dto: IssueDto): ResponseEntity<IssueDto> {
        val entity = this.issueService.save(this.issueMapper.map(dto, null))
        return ResponseEntity.ok(this.issueMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_ISSUE)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: IssueDto): ResponseEntity<IssueDto> {
        var entity = this.issueService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        entity = this.issueService.save(this.issueMapper.map(dto, entity))
        return ResponseEntity.ok(this.issueMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_ISSUE)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.issueService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}