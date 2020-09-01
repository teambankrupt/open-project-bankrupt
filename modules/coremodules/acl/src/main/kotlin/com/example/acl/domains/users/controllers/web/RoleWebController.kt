package com.example.acl.domains.users.controllers.web

import com.example.acl.AclApplication
import com.example.acl.domains.users.models.dtos.RoleDto
import com.example.acl.domains.users.models.mappers.RoleMapper
import com.example.acl.domains.users.services.PrivilegeService
import com.example.acl.domains.users.services.RoleService
import com.example.acl.routing.Route
import com.example.common.utils.ExceptionUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class RoleWebController @Autowired constructor(
        private val rolesService: RoleService,
        private val privilegeService: PrivilegeService,
        private val roleMapper: RoleMapper
) {

    @GetMapping(Route.V1.WEB_ROLES_PAGE)
    fun rolesPage(model: Model): String {
        val roles = this.rolesService.findAll()
        val privileges = this.privilegeService.findAll()

        model.addAttribute("roles", roles)
        model.addAttribute("privileges", privileges)
        return "material/fragments/roles/roles"
    }

    @PostMapping(Route.V1.WEB_ROLE_CREATE)
    fun createRole(@Valid @ModelAttribute roleDto: RoleDto): String {
        val role = this.rolesService.save(this.roleMapper.map(roleDto, null))
        return "redirect:/admin/roles/${role.id}"
    }

    @GetMapping(Route.V1.WEB_ROLE_DETAILS_PAGE)
    fun roleDetailsPage(@PathVariable("role_id") roleId: Long,
                        model: Model): String {

        val selectedRole = this.rolesService.find(roleId).orElseThrow { ExceptionUtil.notFound("Role", roleId) }

        val roles = this.rolesService.findAll()
        val privileges = this.privilegeService.findAll()

        model.addAttribute("selectedRole", selectedRole)
        model.addAttribute("roles", roles)
        model.addAttribute("privileges", privileges)
        return "material/fragments/roles/roles"
    }

    @PostMapping(Route.V1.WEB_ROLE_UPDATE)
    fun updateRole(@PathVariable("role_id") roleId: Long,
                   @Valid @ModelAttribute roleDto: RoleDto): String {
        var role = this.rolesService.find(roleId).orElseThrow { ExceptionUtil.notFound("Role", roleId) }
//        if (role.isAdmin()) throw ExceptionUtil.forbidden("Updating super admin is not possible.")
        role = this.rolesService.save(this.roleMapper.map(roleDto, role))
        return "redirect:/admin/roles/${role.id}"
    }

}