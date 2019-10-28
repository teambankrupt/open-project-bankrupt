package com.example.webservice.domains.home.controllers.admin

import com.example.webservice.config.InitConfig
import com.example.webservice.config.security.SecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminHomeController {
    @Autowired
    private lateinit var initConfig: InitConfig

    @GetMapping("")
    fun home(): String {
        return "index"
    }

    @GetMapping("/login")
    fun loginPage(): String {
        if (SecurityConfig.isAuthenticated())
            return "redirect:/admin/dashboard"
        return "material/pages/login"
    }

    @GetMapping("/admin/dashboard")
    fun dashboard(): String {
        return "material/fragments/dashboard/dashboard"
    }

    //TODO: TO BE REMOVED
    @GetMapping("/init")
    fun init(): String {
        this.initConfig.onBootUp()
        return "index"
    }
}
