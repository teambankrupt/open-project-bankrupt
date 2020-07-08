package com.example.application.domains.address.controllers

import com.example.application.commons.Constants
import com.example.application.commons.utils.ExceptionUtil
import com.example.application.domains.address.models.dto.VillageDto
import com.example.application.domains.address.models.entities.Village
import com.example.application.domains.address.models.mappers.VillageMapper
import com.example.application.domains.address.services.VillageService
import com.example.application.domains.common.base.controllers.CrudController
import com.example.application.routing.Route
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = [Constants.Swagger.VILLAGE], description = Constants.Swagger.REST_API)
class VillageController @Autowired constructor(
        private val villageService: VillageService,
        private val villageMapper: VillageMapper
) : CrudController<VillageDto> {

    @GetMapping(Route.V1.SEARCH_VILLAGES)
    @ApiOperation(value = Constants.Swagger.SEARCH_ALL_MSG + Constants.Swagger.VILLAGE)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<VillageDto>> {
        val villages: Page<Village> = this.villageService.search(query, page, size)
        return ResponseEntity.ok(villages.map { village -> this.villageMapper.map(village) })
    }

    @PostMapping(Route.V1.CREATE_VILLAGES)
    @ApiOperation(value = Constants.Swagger.POST_MSG + Constants.Swagger.VILLAGE)
    override fun create(@Valid @RequestBody dto: VillageDto): ResponseEntity<VillageDto> {
        val village: Village = this.villageService.save(this.villageMapper.map(dto, null))
        return ResponseEntity.ok(this.villageMapper.map(village))
    }

    @GetMapping(Route.V1.FIND_VILLAGES)
    @ApiOperation(value = Constants.Swagger.GET_MSG + Constants.Swagger.VILLAGE)
    override fun find(@PathVariable id: Long): ResponseEntity<VillageDto> {
        val village: Village = this.villageService.find(id).orElseThrow { ExceptionUtil.getNotFound("village", id) }
        return ResponseEntity.ok(this.villageMapper.map(village))
    }

    @PatchMapping(Route.V1.UPDATE_VILLAGES)
    @ApiOperation(value = Constants.Swagger.PATCH_MSG + Constants.Swagger.VILLAGE)
    override fun update(@PathVariable id: Long, @Valid @RequestBody dto: VillageDto): ResponseEntity<VillageDto> {
        var village: Village = villageService.find(id).orElseThrow { ExceptionUtil.getNotFound("village", id) }
        village = villageService.save(villageMapper.map(dto, village))
        return ResponseEntity.ok(villageMapper.map(village))
    }

    @DeleteMapping(Route.V1.DELETE_VILLAGES)
    @ApiOperation(value = Constants.Swagger.DELETE_MSG + Constants.Swagger.VILLAGE)
    override fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        this.villageService.delete(id, true)
        return ResponseEntity.ok().build()
    }
}
