package com.exemple.controller;

import com.exemple.model.*;
import com.exemple.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livre")
public class LivreController {
    
    @Autowired
    private LivreService livreService;
    
    @Autowired
    private PretService pretService;
    
    @GetMapping
    public String listLivres(Model model) {
        model.addAttribute("livres", livreService.getAllLivres());
        return "livre/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("livre", new Livre());
        return "livre/add";
    }

    @PostMapping("/save")
    public String saveLivre(@ModelAttribute Livre livre) {
        livreService.saveLivre(livre);
        return "redirect:/livres";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("livre", livreService.getLivreById(id));
        return "livre/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteLivre(@PathVariable Integer id) {
        livreService.deleteLivre(id);
        return "redirect:/livres";
    }

    @GetMapping("/pret/{idLivre}")
    public String showPretForm(@PathVariable int idLivre, Model model) {
        model.addAttribute("idLivre", idLivre);
        return "livre/pret";
    }

    @PostMapping("/pret")
    public String processPret(
            @RequestParam("id_exemplaire") int idExemplaire,
            @RequestParam("id_adherent") int idAdherent,
            Model model) {
        
        String result = pretService.emprunterExemplaire(idExemplaire, idAdherent);
        model.addAttribute("message", result);
        
        return "redirect:/";
    }
}