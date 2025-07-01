package com.exemple.controller;

import com.exemple.model.Bibliothecaire;
import com.exemple.service.BibliothecaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bibliothecaires")
public class BibliothecaireController {
    @Autowired
    private BibliothecaireService bibliothecaireService;

    @GetMapping
    public String listBibliothecaires(Model model) {
        model.addAttribute("bibliothecaires", bibliothecaireService.getAllBibliothecaires());
        return "bibliothecaire/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("bibliothecaire", new Bibliothecaire());
        return "bibliothecaire/add";
    }

    @PostMapping("/save")
    public String saveBibliothecaire(@ModelAttribute Bibliothecaire bibliothecaire) {
        bibliothecaireService.saveBibliothecaire(bibliothecaire);
        return "redirect:/bibliothecaires";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("bibliothecaire", bibliothecaireService.getBibliothecaireById(id));
        return "bibliothecaire/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteBibliothecaire(@PathVariable Integer id) {
        bibliothecaireService.deleteBibliothecaire(id);
        return "redirect:/bibliothecaires";
    }
}