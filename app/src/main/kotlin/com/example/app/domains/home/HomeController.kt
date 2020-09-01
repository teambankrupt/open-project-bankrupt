package com.example.app.domains.home

import com.example.app.MainApplication
import com.example.app.routing.Route
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class HomeController {

    @GetMapping("")
    fun home(): String {
        return "index"
    }

    @PostMapping(Route.V1.WEB_RELOAD_APPLICATION_CONTEXT)
    fun reloadApplicationContext(): String {
        MainApplication.restart()
        return "redirect:${com.example.acl.routing.Route.V1.WEB_ROLES_PAGE}"
    }

    @PostMapping(Route.V1.WEB_SHUTDOWN_APPLICATION_CONTEXT)
    fun closeApplicationContext() {
        MainApplication.terminate()
    }
}