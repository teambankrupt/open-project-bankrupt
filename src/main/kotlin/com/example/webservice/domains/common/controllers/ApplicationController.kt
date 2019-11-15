package com.example.webservice.domains.common.controllers

import com.example.webservice.WebserviceApplication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class ApplicationController {

    @PostMapping("/app/context/refresh")
    fun refreshAppContext() {
        WebserviceApplication.restart()
    }

}
