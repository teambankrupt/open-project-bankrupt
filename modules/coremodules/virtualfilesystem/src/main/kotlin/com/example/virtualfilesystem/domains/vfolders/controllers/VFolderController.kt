package com.example.virtualfilesystem.domains.vfolders.controllers

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudController
import com.example.virtualfilesystem.domains.vfiles.models.dtos.VFileDto
import com.example.virtualfilesystem.domains.vfiles.models.mappers.VFileMapper
import com.example.virtualfilesystem.domains.vfiles.service.VFileService
import com.example.virtualfilesystem.domains.vfolders.models.dtos.VFolderDto
import com.example.virtualfilesystem.domains.vfolders.models.mappers.VFolderMapper
import com.example.virtualfilesystem.domains.vfolders.services.VFolderService
import com.example.virtualfilesystem.routing.Route
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = ["VFolders"], description = "Description about VFolders")
class VFolderController @Autowired constructor(
        private val vFolderService: VFolderService,
        private val vFolderMapper: VFolderMapper,
        private val vFileService: VFileService,
        private val vFileMapper: VFileMapper
) : CrudController<VFolderDto> {

    @GetMapping(Route.V1.SEARCH_VFOLDERS)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<VFolderDto>> {
        val entities = this.vFolderService.search(query, page, size);
        return ResponseEntity.ok(entities.map { this.vFolderMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_VFOLDER)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<VFolderDto> {
        val entity = this.vFolderService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        return ResponseEntity.ok(this.vFolderMapper.map(entity))
    }

    @GetMapping(Route.V1.FIND_FILES_IN_VFOLDER)
    fun findFilesInFolder(@PathVariable("folder_id") folderId: Long,
                          @RequestParam("page", defaultValue = "0") page: Int,
                          @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<List<VFileDto>> {
        val entities = this.vFileService.getFilesInFolder(folderId, page, size)
        return ResponseEntity.ok(entities.map { this.vFileMapper.map(it) })
    }

    @PostMapping(Route.V1.CREATE_VFOLDER)
    override fun create(@Valid @RequestBody dto: VFolderDto): ResponseEntity<VFolderDto> {
        val entity = this.vFolderService.save(this.vFolderMapper.map(dto, null))
        return ResponseEntity.ok(this.vFolderMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_VFOLDER)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: VFolderDto): ResponseEntity<VFolderDto> {
        var entity = this.vFolderService.find(id).orElseThrow { ExceptionUtil.notFound("Example", id) }
        entity = this.vFolderService.save(this.vFolderMapper.map(dto, entity))
        return ResponseEntity.ok(this.vFolderMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_VFOLDER)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.vFolderService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}