package com.example.webservice.domains.home.controllers.admin

import com.example.webservice.config.security.SecurityContext
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
        if (SecurityContext.isAuthenticated())
            return "redirect:/admin/dashboard"
        return "material/pages/login"
    }

    @GetMapping("/admin/dashboard")
    fun dashboard(): String {
        return "material/fragments/dashboard/dashboard"
    }

}
