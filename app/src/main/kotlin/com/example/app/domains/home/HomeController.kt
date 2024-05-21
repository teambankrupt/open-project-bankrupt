package com.example.app.domains.home

import com.example.auth.config.security.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class HomeController {

    @GetMapping("")
    fun home(model: Model): String {
        val auth = SecurityContext.getCurrentUser()
        model.addAttribute("auth", auth)
        return "index"
    }

}