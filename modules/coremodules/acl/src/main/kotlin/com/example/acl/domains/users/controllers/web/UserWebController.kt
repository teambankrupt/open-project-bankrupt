package com.example.acl.domains.users.controllers.web

import com.example.common.utils.ExceptionUtil
import com.example.acl.domains.users.models.dtos.UserUpdateAdminDto
import com.example.acl.domains.users.models.mappers.UserMapper
import com.example.acl.domains.users.services.RoleService
import com.example.acl.domains.users.services.UserService
import com.example.acl.routing.Route
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
) {

    @GetMapping(Route.V1.WEB_USERS_SEARCH_PAGE)
    fun search(@RequestParam("query", defaultValue = "") query: String,
               @RequestParam(value = "role", required = false) role: String?,
               @RequestParam("page", defaultValue = "0") page: Int,
               @RequestParam("size", defaultValue = "10") size: Int,
               model: Model): String {
        val users = this.userService.search(query, role, page, size)

        model.addAttribute("query", query)
        model.addAttribute("users", users)
        return "material/fragments/users/users"
    }

    @GetMapping(Route.V1.WEB_USERS_UPDATE)
    fun detailsPage(@PathVariable("user_id") id: Long,
                    @RequestParam("query", defaultValue = "") query: String,
                    @RequestParam(value = "role", required = false) role: String?,
                    @RequestParam("page", defaultValue = "0") page: Int,
                    @RequestParam("size", defaultValue = "10") size: Int,
                    model: Model): String {
        val user = this.userService.find(id).orElseThrow { ExceptionUtil.notFound("User", id) }

        model.addAttribute("query", query)
        model.addAttribute("user", user)
        model.addAttribute("users", this.userService.search(query, role, page, size))
        model.addAttribute("roles", this.roleService.findAll())
        return "material/fragments/users/details"
    }

    @PostMapping(Route.V1.WEB_USERS_UPDATE)
    fun update(@PathVariable("user_id") id: Long,
               @Valid @ModelAttribute dto: UserUpdateAdminDto): String {
        var user = this.userService.find(id).orElseThrow { ExceptionUtil.notFound("User", id) }
        user = this.userService.save(this.userMapper.map(dto, user))
        return "redirect:/admin/users/${user.id}"
    }


    @GetMapping(Route.V1.WEB_TOGGLE_ENABLED)
    fun toggleEnabled(@PathVariable("user_id") userId: Long,
                      @RequestParam("enable") enable: Boolean): String {
        this.userService.toggleAccess(userId, enable)
        return "redirect:${Route.V1.WEB_USERS_SEARCH_PAGE}"
    }

}