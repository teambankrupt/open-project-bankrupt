package com.example.application.domains.home.controllers.admin

import com.example.application.config.security.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
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
