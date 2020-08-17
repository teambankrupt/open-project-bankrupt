package com.example.app.domains.crudexample.controllers.web

import com.example.app.domains.crudexample.models.dtos.CrudExampleDto
import com.example.app.domains.crudexample.models.mappers.CrudExampleMapper
import com.example.app.domains.crudexample.services.CrudExampleService
import com.example.app.routing.Route
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudWebController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid

@Controller
class CrudExampleWebController @Autowired constructor(
        private val crudExampleService: CrudExampleService,
        private val crudExampleMapper: CrudExampleMapper
) : CrudWebController<CrudExampleDto> {

    /*
        COPY THESE URLS TO ROUTE FILE AND ADJUST
        ------------------------------------------------------
        const val ADMIN_SEARCH_CRUDEXAMPLES = "$ADMIN/crudexamples"
        const val ADMIN_CREATE_CRUDEXAMPLE_PAGE = "$ADMIN/crudexamples/create"
        const val ADMIN_CREATE_CRUDEXAMPLE = "$ADMIN/crudexamples"
        const val ADMIN_FIND_CRUDEXAMPLE = "$ADMIN/crudexamples/{id}"
        const val ADMIN_UPDATE_CRUDEXAMPLE_PAGE = "$ADMIN/crudexamples/{id}/update"
        const val ADMIN_UPDATE_CRUDEXAMPLE = "$ADMIN/crudexamples/{id}"
        const val ADMIN_DELETE_CRUDEXAMPLE = "$ADMIN/crudexamples/{id}/delete"
    */

    @GetMapping(Route.V1.ADMIN_SEARCH_CRUDEXAMPLES)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        model: Model): String {
        val entities = this.crudExampleService.search(query, page, size)
        model.addAttribute("crudexamples", entities)
        return "crudexamples/fragments/all"
    }

    @GetMapping(Route.V1.ADMIN_FIND_CRUDEXAMPLE)
    override fun find(@PathVariable("id") id: Long,
                      model: Model): String {
        val entity = this.crudExampleService.find(id).orElseThrow { ExceptionUtil.notFound("CrudExample", id) }
        model.addAttribute("crudexample", entity)
        return "crudexamples/fragments/details"
    }

    @GetMapping(Route.V1.ADMIN_CREATE_CRUDEXAMPLE_PAGE)
    override fun createPage(model: Model): String {
        return "crudexamples/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_CREATE_CRUDEXAMPLE)
    override fun create(@Valid @ModelAttribute dto: CrudExampleDto,
                        redirectAttributes: RedirectAttributes): String {
        val entity = this.crudExampleService.save(this.crudExampleMapper.map(dto, null))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_CRUDEXAMPLE.replace("{id}", entity.id.toString())}"
    }

    @GetMapping(Route.V1.ADMIN_UPDATE_CRUDEXAMPLE_PAGE)
    override fun updatePage(@PathVariable("id") id: Long, model: Model): String {
        val entity = this.crudExampleService.find(id).orElseThrow { ExceptionUtil.notFound("CrudExample", id) }
        model.addAttribute("crudexample", entity)
        return "crudexamples/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_UPDATE_CRUDEXAMPLE)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @ModelAttribute dto: CrudExampleDto,
                        redirectAttributes: RedirectAttributes): String {
        var entity = this.crudExampleService.find(id).orElseThrow { ExceptionUtil.notFound("CrudExample", id) }
        entity = this.crudExampleService.save(this.crudExampleMapper.map(dto, entity))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_CRUDEXAMPLE.replace("{id}", entity.id.toString())}"
    }

    @PostMapping(Route.V1.ADMIN_DELETE_CRUDEXAMPLE)
    override fun delete(@PathVariable("id") id: Long,
                        redirectAttributes: RedirectAttributes): String {
        this.crudExampleService.delete(id, true)
        redirectAttributes.addFlashAttribute("message", "Deleted!!")
        return "redirect:${Route.V1.ADMIN_SEARCH_CRUDEXAMPLES}";
    }

}