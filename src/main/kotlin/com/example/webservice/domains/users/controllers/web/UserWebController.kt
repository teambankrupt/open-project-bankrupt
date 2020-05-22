package com.example.webservice.domains.users.controllers.web

import com.example.webservice.commons.utils.ExceptionUtil
import com.example.webservice.domains.common.controller.CrudWebController
import com.example.webservice.domains.users.models.dtos.UserUpdateAdminDto
import com.example.webservice.domains.users.models.dtos.UserRequest
import com.example.webservice.domains.users.models.mappers.UserMapper
import com.example.webservice.domains.users.services.RoleService
import com.example.webservice.domains.users.services.UserService
import com.example.webservice.routing.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
class UserWebController @Autowired constructor(
        private val userService: UserService,
        private val roleService: RoleService,
        private val userMapper: UserMapper
) : CrudWebController<UserRequest> {

    @GetMapping(Route.V1.WEB_USERS_SEARCH_PAGE)
    override fun search(@RequestParam("query", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("query", defaultValue = "10") size: Int,
                        model: Model): String {
        val users = this.userService.search(query, page, size)
        model.addAttribute("users", users)
        return "material/fragments/users/users"
    }

    @GetMapping(Route.V1.WEB_USERS_UPDATE)
    fun detailsPage(@PathVariable("user_id") id: Long,
                    @RequestParam("q", defaultValue = "") query: String,
                    @RequestParam("page", defaultValue = "0") page: Int,
                    @RequestParam("query", defaultValue = "10") size: Int,
                    model: Model): String {
        val user = this.userService.find(id).orElseThrow { ExceptionUtil.getNotFound("User", id) }

        model.addAttribute("user", user)
        model.addAttribute("users", this.userService.search(query, page, size))
        model.addAttribute("roles", this.roleService.findAll())
        return "material/fragments/users/details"
    }

    @PostMapping(Route.V1.WEB_USERS_UPDATE)
    fun update(@PathVariable("user_id") id: Long,
               @Valid @ModelAttribute dto: UserUpdateAdminDto): String {
        var user = this.userService.find(id).orElseThrow { ExceptionUtil.getNotFound("User", id) }
        user = this.userService.save(this.userMapper.map(dto, user))
        return "redirect:/admin/users/${user.id}"
    }

    override fun find(id: Long): String {
        TODO("Not yet implemented")
    }

    override fun createPage(model: Model): String {
        TODO("Not yet implemented")
    }

    override fun create(dto: UserRequest): String {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): String {
        TODO("Not yet implemented")
    }

    @GetMapping(Route.V1.WEB_TOGGLE_ENABLED)
    fun toggleEnabled(@PathVariable("user_id") userId: Long,
                      @RequestParam("enable") enable: Boolean): String {
        this.userService.toggleAccess(userId, enable)
        return "redirect:${Route.V1.WEB_USERS_SEARCH_PAGE}"
    }

    override fun updatePage(id: Long, model: Model): String {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, dto: UserRequest): String {
        TODO("Not yet implemented")
    }


}