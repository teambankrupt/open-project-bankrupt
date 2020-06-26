package com.example.webservice.domains.common.global.controllers

import com.example.webservice.WebserviceApplication
import com.example.webservice.commons.Constants
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/application")
@Api(tags = [Constants.Swagger.GLOBAL_API], description = Constants.Swagger.GLOBAL_API_DETAILS)
class ApplicationController {

    @PatchMapping("/context/reload")
    @ApiOperation(value = Constants.Swagger.RELOAD_CONTEXT)
    fun reloadApplicationContext() {
        WebserviceApplication.restart()
    }

}