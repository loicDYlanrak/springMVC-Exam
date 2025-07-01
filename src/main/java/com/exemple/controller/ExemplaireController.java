package com.exemple.controller;

import com.exemple.model.Exemplaire;
import com.exemple.model.Livre;
import com.exemple.service.ExemplaireService;
import com.exemple.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exemplaires")
public class ExemplaireController {
    @Autowired
    private ExemplaireService exemplaireService;
    
    @Autowired
    private LivreService livreService;

    @GetMapping
    public String listExemplaires(Model model) {
        model.addAttribute("exemplaires", exemplaireService.getAllExemplaires());
        return "exemplaire/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        Exemplaire exemplaire = new Exemplaire();
        List<Livre> livres = livreService.getAllLivres();
        
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("livres", livres);
        return "exemplaire/add";
    }

    @PostMapping("/save")
    public String saveExemplaire(@ModelAttribute Exemplaire exemplaire) {
        exemplaireService.saveExemplaire(exemplaire);
        return "redirect:/exemplaires";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Exemplaire exemplaire = exemplaireService.getExemplaireById(id);
        List<Livre> livres = livreService.getAllLivres();
        
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("livres", livres);
        return "exemplaire/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteExemplaire(@PathVariable Integer id) {
        exemplaireService.deleteExemplaire(id);
        return "redirect:/exemplaires";
    }
}