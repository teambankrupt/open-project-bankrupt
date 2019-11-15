package com.example.webservice.domains.users.controllers

import com.example.webservice.config.security.TokenService
import com.example.webservice.domains.users.models.entities.User
import com.example.webservice.domains.users.models.mappers.UserMapper
import com.example.webservice.domains.users.services.UserService
import com.example.webservice.exceptions.exists.UserAlreadyExistsException
import com.example.webservice.exceptions.forbidden.ForbiddenException
import com.example.webservice.exceptions.invalid.InvalidException
import com.example.webservice.exceptions.invalid.UserInvalidException
import com.example.webservice.exceptions.notfound.NotFoundException
import com.example.webservice.exceptions.notfound.UserNotFoundException
import com.example.webservice.exceptions.nullpointer.NullPasswordException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin/users")
class UserController @Autowired
constructor(
        val userService: UserService,
        val userMapper: UserMapper,
        val tokenService: TokenService
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
        this.tokenService.revokeAuthentication(user)
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
