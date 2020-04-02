package com.example.webservice.chat.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/c")
class ChatClientController {

    @GetMapping("")
    fun chatPage(): String {
        return "material/pages/chat"
    }
}