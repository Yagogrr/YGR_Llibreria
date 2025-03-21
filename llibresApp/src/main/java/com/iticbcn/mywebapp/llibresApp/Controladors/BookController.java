package com.iticbcn.mywebapp.llibresApp.Controladors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.iticbcn.mywebapp.llibresApp.Repositoris.RepoLlibre;

@Controller
@SessionAttributes("users")
public class BookController {
    
    @Autowired
    RepoLlibre repoll = new RepoLlibre();

    @GetMapping("/")
    public String iniciar(Model model) {
        return "login";
    }
}