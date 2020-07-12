package com.example.acl.domains.address.controllers

import com.example.app.Constants
import com.example.app.utils.ExceptionUtil
import com.example.acl.domains.address.models.dto.DistrictDto
import com.example.acl.domains.address.models.entities.District
import com.example.acl.domains.address.models.mappers.DistrictMapper
import com.example.acl.domains.address.services.DistrictService
import com.example.coreweb.domains.base.controllers.CrudController
import com.example.acl.routing.Route
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = [Constants.Swagger.DISTRICT], description = Constants.Swagger.REST_API)
class DistrictController(
        @Autowired val districtService: DistrictService,
        @Autowired val districtMapper: DistrictMapper
) : CrudController<DistrictDto> {

    @GetMapping(Route.V1.SEARCH_DISTRICT)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.DISTRICT)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<DistrictDto>> {
        val districts: Page<District> = this.districtService.search(query, page, size)
        return ResponseEntity.ok(districts.map { district -> this.districtMapper.map(district) })
    }

    @GetMapping(Route.V1.FIND_DISTRICT)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.DISTRICT)
    override fun find(@PathVariable id: Long): ResponseEntity<DistrictDto> {
        val district: District = this.districtService.find(id).orElseThrow { ExceptionUtil.getNotFound("district", id) }
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @PostMapping(Route.V1.CREATE_DISTRICT)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.DISTRICT)
    override fun create(@Valid @RequestBody dto: DistrictDto): ResponseEntity<DistrictDto> {
        val district: District = this.districtService.save(this.districtMapper.map(dto, null))
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @PatchMapping(Route.V1.UPDATE_DISTRICT)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.DISTRICT)
    override fun update(@PathVariable id: Long, @Valid @RequestBody dto: DistrictDto): ResponseEntity<DistrictDto> {
        var district: District = this.districtService.find(id).orElseThrow { ExceptionUtil.getNotFound("district", id) }
        district = this.districtService.save(this.districtMapper.map(dto, district))
        return ResponseEntity.ok(this.districtMapper.map(district))
    }

    @DeleteMapping(Route.V1.DELETE_DISTRICT)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.DISTRICT)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.districtService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
