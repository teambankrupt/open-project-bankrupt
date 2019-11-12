package com.example.webservice.domains.home.controllers.api

import com.example.webservice.config.security.SecurityConfig
import com.example.webservice.config.security.TokenService
import com.example.webservice.domains.users.models.dtos.UserDto
import com.example.webservice.domains.users.models.mappers.UserMapper
import com.example.webservice.domains.users.services.UserServiceV2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/v2")
class ApiHomeControllerV2 @Autowired constructor(
        val userService: UserServiceV2,
        val tokenService: TokenService,
        val userMapper: UserMapper
) {
    @Value("\${baseUrl}")
    val baseUrl: String? = null

    @Value("\${token.validity}")
    val tokenValidity: String? = null

    @GetMapping("")
    fun index(): ResponseEntity<*> {
        val headers = HttpHeaders()
        headers.location = URI.create("/swagger-ui.html")
        return ResponseEntity(null, headers, HttpStatus.TEMPORARY_REDIRECT)
    }

    @PostMapping("/register/verify")
    fun verifyPhone(@RequestParam("identity") phoneOrEmail: String): ResponseEntity<Any> {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis() + Integer.parseInt(this.tokenValidity)
        val sent = this.userService.requireAccountValidationByOTP(phoneOrEmail, calendar.time)

        return if (!sent) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build()
        else ResponseEntity.ok("OTP sent!")
    }

    @PostMapping("/register")
    fun register(@RequestParam("token") token: String,
                 @RequestBody userDto: UserDto): ResponseEntity<Any> {

        val user = this.userService.register(token, this.userMapper.map(userDto, null))

        SecurityConfig.updateAuthentication(user)
        return ResponseEntity.ok(tokenService.createAccessToken(user))
    }


}
