package com.exemple.controller;

import com.exemple.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private LivreService livreService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("livres", livreService.getAllLivres().stream()
                .filter(l -> l.getTitre().toLowerCase().contains(search.toLowerCase()))
                .toList());
        } else {
            model.addAttribute("livres", livreService.getAllLivres());
        }
        return "home";
    }
}