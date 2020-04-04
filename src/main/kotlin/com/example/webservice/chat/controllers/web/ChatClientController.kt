package com.example.webservice.chat.controllers.web

import com.example.webservice.config.security.SecurityContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/messages")
class ChatClientController {

    @GetMapping("")
    fun chatPage(model: Model): String {
        model.addAttribute("username", SecurityContext.getLoggedInUsername())
        return "material/pages/chat2"
    }
}