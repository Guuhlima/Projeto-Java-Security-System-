package org.example.auth.controller;

import org.example.auth.service.AuthService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    // LOGIN
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "auth/login";
    }

    // REGISTRO
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam @Email String email,
                           @RequestParam @NotBlank String password,
                           @RequestParam @NotBlank String confirm,
                           Model model) {
        if (!password.equals(confirm)) {
            model.addAttribute("error", "As senhas não conferem");
            return "auth/register";
        }
        try {
            auth.register(email, password);
            return "redirect:/auth/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    // ESQUECI A SENHA
    @GetMapping("/forgot")
    public String forgotPage() {
        return "auth/forgot";
    }

    @PostMapping("/forgot")
    public String forgot(@RequestParam @Email String email, Model model) {
        try {
            String token = auth.createResetToken(email);
            // TODO: enviar e-mail com link real. Por enquanto, mostre na UI:
            model.addAttribute("info", "Use o link: /auth/reset?token=" + token + " (válido por 1h)");
            return "auth/forgot";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/forgot";
        }
    }

    // RESET
    @GetMapping("/reset")
    public String resetPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "auth/reset";
    }

    @PostMapping("/reset")
    public String reset(@RequestParam String token,
                        @RequestParam String password,
                        @RequestParam String confirm,
                        Model model) {
        if (!password.equals(confirm)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "As senhas não conferem");
            return "auth/reset";
        }
        try {
            auth.resetPassword(token, password);
            return "redirect:/auth/login?resetOk";
        } catch (IllegalArgumentException e) {
            model.addAttribute("token", token);
            model.addAttribute("error", e.getMessage());
            return "auth/reset";
        }
    }
}