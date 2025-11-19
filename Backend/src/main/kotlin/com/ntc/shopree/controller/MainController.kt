package com.ntc.shopree.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDateTime

@Controller
class MainController {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("title", "Home")
        model.addAttribute("welcome", "Welcome to Shopree with JTE!")
        model.addAttribute("now", LocalDateTime.now())
        return "pages/home"
    }
}