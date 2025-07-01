package com.exemple.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Salama Daholo !");
        return "accueil"; 
    }
}