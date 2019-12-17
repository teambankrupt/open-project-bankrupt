package com.example.webservice.domains.home.controllers.api

import com.example.webservice.config.security.SecurityContext
import com.example.webservice.config.security.TokenService
import com.example.webservice.domains.users.models.UserAuth
import com.example.webservice.domains.users.models.dtos.UserRequest
import com.example.webservice.domains.users.models.mappers.UserMapper
import com.example.webservice.domains.users.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1")
class ApiHomeController @Autowired constructor(
        val userService: UserService,
        val tokenService: TokenService,
        val userMapper: UserMapper
) {
    @Value("\${baseUrl}")
    val baseUrl: String? = null

    @Value("\${token.validity}")
    val tokenValidity: String? = null

    @PostMapping("/register/verify")
    fun verifyIdentity(@RequestParam("identity") phoneOrEmail: String): ResponseEntity<Any> {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis() + Integer.parseInt(this.tokenValidity)
        val sent = this.userService.requireAccountValidationByOTP(phoneOrEmail, calendar.time)

        return if (!sent) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build()
        else ResponseEntity.ok("OTP sent!")
    }

    @PostMapping("/register")
    fun register(@RequestParam("token") token: String,
                 @RequestBody userDto: UserRequest): ResponseEntity<Any> {

        val user = this.userService.register(token, this.userMapper.map(userDto, null))

        SecurityContext.updateAuthentication(UserAuth(user))
        return ResponseEntity.ok(tokenService.createAccessToken(user))
    }


    @PostMapping("/change_password")
    fun changePassword(@RequestParam("current_password") currentPassword: String,
                       @RequestParam("new_password") newPassword: String): ResponseEntity<Any> {
        this.userService.changePassword(SecurityContext.getCurrentUser().id, currentPassword, newPassword)
        return ResponseEntity.ok().build()
    }

    // Password reset
    @GetMapping("/reset_password")
    @ResponseStatus(HttpStatus.OK)
    fun requestResetPassword(@RequestParam("username") username: String) {
        this.userService.handlePasswordResetRequest(username)
    }

    @PostMapping("/reset_password")
    @ResponseStatus(HttpStatus.OK)
    fun resetPassword(@RequestParam("username") username: String,
                      @RequestParam("token") token: String,
                      @RequestParam("password") password: String) {
        this.userService.resetPassword(username, token, password)
    }

}
