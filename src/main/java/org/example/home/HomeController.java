package org.example.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        model.addAttribute("mensagem", "Você está logado! Bem-vindo à dashboard.");
        if (principal != null) {
            model.addAttribute("user", principal.getName()); // normalmente o e-mail/username
        }
        return "index";
    }
}
