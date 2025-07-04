package com.exemple.controller;

import com.exemple.model.Bibliothecaire;
import com.exemple.model.TypeAdherent;
import com.exemple.service.AdherentService;
import com.exemple.service.AbonnementService;
import com.exemple.service.TypeAdherentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired
    private AdherentService adherentService;

    @Autowired 
    private AbonnementService abonnementService;

    @Autowired
    private TypeAdherentService typeAdherentService;

    @GetMapping("/form")
    public String showInscriptionForm(HttpSession session, Model model) {
        // Vérifier si un bibliothécaire est connecté
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (bibliothecaire == null) {
            return "redirect:/login";
        }

        List<TypeAdherent> typesAdherent = typeAdherentService.getAllTypeAdherents();
        model.addAttribute("typesAdherent", typesAdherent);
        return "/inscription/form";
    }

    @PostMapping("/adherent")
    public String inscrireAdherent(
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String pwd,
            @RequestParam String dateNaissance,
            @RequestParam Integer idTypeAdherent,
            HttpSession session,
            Model model) {

        // Vérifier si un bibliothécaire est connecté
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (bibliothecaire == null) {
            return "redirect:/login";
        }

        try {
            adherentService.inscrireAdherent(nom, email, pwd, dateNaissance, idTypeAdherent);
            model.addAttribute("message", "Adhérent inscrit avec succès!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return showInscriptionForm(session, model);
    }

    @PostMapping("/abonnement")
    public String creerAbonnement(
            @RequestParam Integer idAdherent,
            @RequestParam String dateDebut,
            @RequestParam String dateFin,
            HttpSession session,
            Model model) {

        // Vérifier si un bibliothécaire est connecté
        Bibliothecaire bibliothecaire = (Bibliothecaire) session.getAttribute("bibliothecaire");
        if (bibliothecaire == null) {
            return "redirect:/login";
        }

        try {
            abonnementService.creerAbonnement(idAdherent, dateDebut, dateFin);
            model.addAttribute("message", "Abonnement créé avec succès!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return showInscriptionForm(session, model);
    }
}