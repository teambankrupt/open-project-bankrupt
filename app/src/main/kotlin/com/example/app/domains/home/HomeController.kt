package com.example.app.domains.home

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class HomeController {

    @GetMapping("")
    fun home(): String {
        return "index"
    }

}