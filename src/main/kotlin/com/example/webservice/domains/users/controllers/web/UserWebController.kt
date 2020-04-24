package com.example.webservice.domains.users.controllers.web

import com.example.webservice.domains.common.controller.CrudWebController
import com.example.webservice.domains.users.models.dtos.UserRequest
import com.example.webservice.domains.users.services.UserService
import com.example.webservice.routing.Route
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class UserWebController @Autowired constructor(
        private val userService: UserService
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

    override fun find(id: Long): String {
        TODO("Not yet implemented")
    }

    override fun createPage(model: Model): String {
        TODO("Not yet implemented")
    }

    override fun create(dto: UserRequest): String {
        TODO("Not yet implemented")
    }

    override fun updatePage(id: Long, model: Model): String {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, dto: UserRequest): String {
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


}