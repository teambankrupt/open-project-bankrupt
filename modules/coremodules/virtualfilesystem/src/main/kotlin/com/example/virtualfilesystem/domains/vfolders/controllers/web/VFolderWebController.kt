package com.example.virtualfilesystem.domains.vfolders.controllers.web

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudWebController
import com.example.virtualfilesystem.domains.vfiles.service.VFileService
import com.example.virtualfilesystem.domains.vfolders.models.dtos.VFolderDto
import com.example.virtualfilesystem.domains.vfolders.models.mappers.VFolderMapper
import com.example.virtualfilesystem.domains.vfolders.services.VFolderService
import com.example.virtualfilesystem.routing.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Controller
@ApiIgnore
class VFolderWebController @Autowired constructor(
        private val vFolderService: VFolderService,
        private val vFolderMapper: VFolderMapper,
        private val vFileService: VFileService
) : CrudWebController<VFolderDto> {

    @GetMapping(Route.V1.ADMIN_SEARCH_VFOLDERS)
    fun search(@RequestParam("q", defaultValue = "") query: String,
               @RequestParam("page", defaultValue = "0") page: Int,
               @RequestParam("size", defaultValue = "10") size: Int,
               @RequestParam("parent_id", required = false) parentId: Long?,
               model: Model): String {
        val entities = this.vFolderService.search(query, parentId, page, size)

        val parent = parentId?.let { this.vFolderService.find(it).orElseThrow { ExceptionUtil.notFound("VFolder", parentId) } }
        val files = parentId?.let { this.vFileService.getFilesInFolder(it, page, size) }

        model.addAttribute("vfolders", entities)
        model.addAttribute("parent", parent)
        model.addAttribute("vfiles",files)

        return "vfolders/fragments/all"
    }

    @GetMapping(Route.V1.ADMIN_FIND_VFOLDER)
    override fun find(@PathVariable("id") id: Long,
                      model: Model): String {
        val entity = this.vFolderService.find(id).orElseThrow { ExceptionUtil.notFound("VFolder", id) }
        model.addAttribute("vfolder", entity)
        return "vfolders/fragments/details"
    }

    //    @GetMapping(Route.V1.ADMIN_CREATE_VFOLDER_PAGE)
    override fun createPage(model: Model): String {
        return "vfolders/fragments/create"
    }

    @GetMapping(Route.V1.ADMIN_CREATE_VFOLDER_PAGE)
    fun createPageWithinParent(@RequestParam("parent_id") parentId: Long?,
                               model: Model): String {
        val parent = parentId?.let { this.vFolderService.find(it).orElseThrow { ExceptionUtil.notFound("VFolder", parentId) } }
        model.addAttribute("parent", parent)
        return "vfolders/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_CREATE_VFOLDER)
    override fun create(@Valid @ModelAttribute dto: VFolderDto,
                        redirectAttributes: RedirectAttributes): String {
        val entity = this.vFolderService.save(this.vFolderMapper.map(dto, null))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_VFOLDER.replace("{id}", entity.id.toString())}"
    }

    //    @GetMapping(Route.V1.ADMIN_UPDATE_VFOLDER_PAGE)
    override fun updatePage(@PathVariable("id") id: Long, model: Model): String {
        val entity = this.vFolderService.find(id).orElseThrow { ExceptionUtil.notFound("VFolder", id) }
        model.addAttribute("vfolder", entity)
        return "vfolders/fragments/create"
    }


    @GetMapping(Route.V1.ADMIN_UPDATE_VFOLDER_PAGE)
    fun updatePageWithinParent(@PathVariable("id") id: Long,
                               @RequestParam("parent_id") parentId: Long?,
                               model: Model): String {
        val entity = this.vFolderService.find(id).orElseThrow { ExceptionUtil.notFound("VFolder", id) }

        val parent = parentId?.let { this.vFolderService.find(it).orElseThrow { ExceptionUtil.notFound("VFolder", parentId) } }
        model.addAttribute("parent", parent)
        model.addAttribute("vfolder", entity)
        return "vfolders/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_UPDATE_VFOLDER)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @ModelAttribute dto: VFolderDto,
                        redirectAttributes: RedirectAttributes): String {
        var entity = this.vFolderService.find(id).orElseThrow { ExceptionUtil.notFound("VFolder", id) }
        entity = this.vFolderService.save(this.vFolderMapper.map(dto, entity))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_VFOLDER.replace("{id}", entity.id.toString())}"
    }

    @PostMapping(Route.V1.ADMIN_DELETE_VFOLDER)
    override fun delete(@PathVariable("id") id: Long,
                        redirectAttributes: RedirectAttributes): String {
        this.vFolderService.delete(id, true)
        redirectAttributes.addFlashAttribute("message", "Deleted!!")
        return "redirect:${Route.V1.ADMIN_SEARCH_VFOLDERS}";
    }

    override fun search(query: String, page: Int, size: Int, model: Model): String {
        TODO("Not yet implemented")
    }

}