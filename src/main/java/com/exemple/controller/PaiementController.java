package com.exemple.controller;

import com.exemple.model.Abonnement;
import com.exemple.model.Adherent;
import com.exemple.service.AbonnementService;
import com.exemple.service.AdherentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/paiement")
public class PaiementController {
    @Autowired
    private AdherentService adherentService;
    
    @Autowired
    private AbonnementService abonnementService;

    @GetMapping
    public String showPaiementForm(@RequestParam Integer adherentId, Model model) {
        Adherent adherent = adherentService.getAdherentById(adherentId);
        model.addAttribute("adherent", adherent);
        return "paiement/form";
    }

    @PostMapping
    public String processPaiement(@RequestParam Integer adherentId) {
        Adherent adherent = adherentService.getAdherentById(adherentId);
        
        // Créer un abonnement d'un an
        Calendar calendar = Calendar.getInstance();
        Date dateDebut = new Date();
        calendar.setTime(dateDebut);
        calendar.add(Calendar.YEAR, 1);
        Date dateFin = calendar.getTime();
        
        Abonnement abonnement = new Abonnement();
        abonnement.setAdherent(adherent);
        abonnement.setDateDebut(dateDebut);
        abonnement.setDateFin(dateFin);
        
        abonnementService.saveAbonnement(abonnement);
        
        return "redirect:/login?success";
    }
}