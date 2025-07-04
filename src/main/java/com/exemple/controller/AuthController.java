package com.exemple.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exemple.model.Adherent;
import com.exemple.model.Bibliothecaire;
import com.exemple.service.AdherentService;
import com.exemple.service.BibliothecaireService;

@Controller
public class AuthController {
    @Autowired
    private AdherentService adherentService;
    
    @Autowired
    private BibliothecaireService bibliothecaireService;

    @GetMapping("/login")
    public String showLoginForm() { 
        return "auth/login";
    }

    @PostMapping("/login/adherent")
    public String loginAdherent(@RequestParam String email, 
                              @RequestParam String password,
                              HttpSession session) {
        Adherent adherent = adherentService.findByEmail(email);
        if (adherent != null && adherent.getPwd().equals(password)) {
            session.setAttribute("adherent", adherent);
            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    @PostMapping("/login/bibliothecaire")
    public String loginBibliothecaire(@RequestParam String nom, 
                                    @RequestParam String password,
                                    HttpSession session) {
        Bibliothecaire bibliothecaire = bibliothecaireService.getBibliothecaireByNom(nom);
        if (bibliothecaire != null && bibliothecaire.getPwd().equals(password)) {
            session.setAttribute("bibliothecaire", bibliothecaire);
            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}