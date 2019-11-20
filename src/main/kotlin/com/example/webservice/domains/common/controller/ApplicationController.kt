package com.example.webservice.domains.common.controller

import com.example.webservice.WebserviceApplication
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/application")
class ApplicationController {

    @PatchMapping("/context/reload")
    fun reloadApplicationContext() {
        WebserviceApplication.restart()
    }

}