package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // prefixo diferente
public class HomeController { // pode manter o nome da classe
    @GetMapping
    public String adminHome() {
        return "admin/index";
    }
}
