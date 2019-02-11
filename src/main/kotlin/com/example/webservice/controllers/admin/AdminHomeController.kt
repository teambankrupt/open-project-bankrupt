package com.example.webservice.controllers.admin

import com.example.webservice.config.security.SecurityConfig
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminHomeController {

    @GetMapping("")
    fun home(): String {
        return "index"
    }

    @GetMapping("/login")
    fun loginPage(): String {
        if (SecurityConfig.isAuthenticated())
            return "redirect:/admin/dashboard"
        return "adminlte/pages/login"
    }

    @GetMapping("/admin/dashboard")
    fun dashboard():String{
        return "adminlte/fragments/dashboard/dashboard"
    }
}