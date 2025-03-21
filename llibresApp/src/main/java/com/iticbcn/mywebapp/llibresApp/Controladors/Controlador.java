package com.iticbcn.mywebapp.llibresApp.Controladors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controlador {
    @GetMapping("/")
    public String iniciar(Model model){
        return "login";//login html
    }

    @PostMapping("/")
    public String login(Model model, @RequestParam String nom, @RequestParam String contraseña) {
        if (nom.equals("ariadna") && contraseña.equals("1234")) {
            return "consulta";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
