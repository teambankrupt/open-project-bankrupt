package com.example.acl.domains.home.controllers.api

import com.example.coreweb.commons.Constants
import com.example.auth.config.security.SecurityContext
import com.example.acl.domains.users.models.dtos.UserRequest
import com.example.acl.domains.users.models.mappers.UserMapper
import com.example.acl.domains.users.services.UserService
import com.example.acl.routing.Route
import com.example.auth.config.security.TokenService
import com.example.auth.entities.UserAuth
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Api(tags = [Constants.Swagger.BASIC_APIS], description = Constants.Swagger.BASIC_API_DETAILS)
@PropertySource("classpath:security.properties")
class ApiHomeController @Autowired constructor(
        private val userService: UserService,
        private val tokenService: TokenService,
        private val userMapper: UserMapper
) {
    @Value("\${baseUrl}")
    val baseUrl: String? = null

    @Value("\${token.validity}")
    lateinit var tokenValidity: String

    @PostMapping(Route.V1.VERIFY_REGISTRATION)
    @ApiOperation(value = Constants.Swagger.VERIFY_PHONE)
    fun verifyIdentity(@RequestParam("identity") phoneOrEmail: String): ResponseEntity<String> {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis() + Integer.parseInt(this.tokenValidity)
        val sent = this.userService.requireAccountValidationByOTP(phoneOrEmail, calendar.time)

        return if (!sent) ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build()
        else ResponseEntity.ok("Token Validity: " + this.tokenValidity + " ms")
    }

    @PostMapping(Route.V1.REGISTER)
    @ApiOperation(value = Constants.Swagger.REGISTER)
    fun register(@RequestParam("token") token: String,
                 @RequestBody userDto: UserRequest): ResponseEntity<OAuth2AccessToken> {

        val user = this.userService.register(token, this.userMapper.map(userDto, null))

        SecurityContext.updateAuthentication(UserAuth(user))
        return ResponseEntity.ok(tokenService.createAccessToken(UserAuth(user)))
    }


    @PostMapping(Route.V1.CHANGE_PASSWORD)
    @ApiOperation(value = Constants.Swagger.CHANGE_PASSWORD)
    fun changePassword(@RequestParam("current_password") currentPassword: String,
                       @RequestParam("new_password") newPassword: String): ResponseEntity<HttpStatus> {
        this.userService.changePassword(SecurityContext.getCurrentUser().id, currentPassword, newPassword)
        return ResponseEntity.ok().build()
    }

    // Password reset
    @GetMapping(Route.V1.RESET_PASSWORD)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = Constants.Swagger.VERIFY_RESET_PASSWORD)
    fun requestResetPassword(@RequestParam("username") username: String) {
        this.userService.handlePasswordResetRequest(username)
    }

    @PostMapping(Route.V1.RESET_PASSWORD)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = Constants.Swagger.RESET_PASSWORD)
    fun resetPassword(@RequestParam("username") username: String,
                      @RequestParam("token") token: String,
                      @RequestParam("password") password: String) {
        this.userService.resetPassword(username, token, password)
    }

}
