package com.exemple.controller;

import com.exemple.model.TypeAdherent;
import com.exemple.service.TypeAdherentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/types-adherent")
public class TypeAdherentController {
    @Autowired
    private TypeAdherentService typeAdherentService;

    @GetMapping
    public String listTypeAdherents(Model model) {
        model.addAttribute("typesAdherent", typeAdherentService.getAllTypeAdherents());
        return "typeAdherent/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("typeAdherent", new TypeAdherent());
        return "typeAdherent/add";
    }

    @PostMapping("/save")
    public String saveTypeAdherent(@ModelAttribute TypeAdherent typeAdherent) {
        typeAdherentService.saveTypeAdherent(typeAdherent);
        return "redirect:/types-adherent";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("typeAdherent", typeAdherentService.getTypeAdherentById(id));
        return "typeAdherent/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteTypeAdherent(@PathVariable Integer id) {
        typeAdherentService.deleteTypeAdherent(id);
        return "redirect:/types-adherent";
    }
}