package com.exemple.controller;

import com.exemple.service.PenaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/penalites")
public class PenaliteController {

    @Autowired
    private PenaliteService penaliteService;

    @GetMapping("/statistiques")
    public String statistiquesPenalites(Model model) {
        model.addAttribute("penalitesStatistiques", penaliteService.getPenalitesStatistiques());
        return "penalitesStatistiques";
    }
}
