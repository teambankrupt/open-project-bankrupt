package com.example.acl.domains.users.controllers

import com.example.coreweb.commons.Constants
import com.example.acl.domains.users.models.mappers.UserMapper
import com.example.acl.domains.users.services.UserService
import com.example.auth.config.security.TokenService
import com.example.auth.entities.UserAuth
import com.example.common.exceptions.notfound.UserNotFoundException
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin/users")
@Api(tags = [Constants.Swagger.USERS_ADMIN], description = Constants.Swagger.USERS_ADMIN_API_DETAILS)
class UserController @Autowired constructor(
        private val userService: UserService,
        private val userMapper: UserMapper,
        private val tokenService: TokenService
) {

    @GetMapping("")
    fun search(@RequestParam("q", defaultValue = "") query: String,
               @RequestParam(value = "role", defaultValue = "User") role: String,
               @RequestParam(value = "page", defaultValue = "0") page: Int,
               @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Any> {

        val userPage = this.userService.search(query, role, page, size)

        return ResponseEntity.ok(userPage.map { this.userMapper.map(it) })
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") userId: Long): ResponseEntity<Any> {
        val user = this.userService.find(userId).orElseThrow { UserNotFoundException("Could not find user with id: $userId") }
        return ResponseEntity.ok(this.userMapper.map(user))
    }


    @PostMapping("/{id}/access/toggle")
    fun disableUser(@PathVariable("id") id: Long,
                    @RequestParam("enabled") enabled: Boolean): ResponseEntity<Any> {
        var user = this.userService.find(id).orElseThrow { UserNotFoundException("Could not find user with id: $id") }
        user.isEnabled = enabled
        user = this.userService.save(user)
        this.tokenService.revokeAuthentication(UserAuth(user))
        return ResponseEntity.ok(this.userMapper.map(user))
    }

    @PutMapping("/{id}/change_role")
    fun changeRole(@PathVariable("id") id: Long,
                   @RequestParam("roles") roles: List<Long>): ResponseEntity<*> {
        val user = this.userService.setRoles(id, roles)
        return ResponseEntity.ok(this.userMapper.map(user))
    }

    @PatchMapping("/{id}/changePassword")
    fun changePassword(@PathVariable("id") userId: Long,
                       @RequestParam("newPassword") newPassword: String): ResponseEntity<Any> {
        this.userService.setPassword(userId, newPassword)
        return ResponseEntity.ok().build()
    }


}
