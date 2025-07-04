package com.exemple.controller;

import com.exemple.model.*;
import com.exemple.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/livre")
public class LivreController {
    
    @Autowired
    private LivreService livreService;
    
    @Autowired
    private ExemplaireService exemplaireService;
    
    
    @Autowired
    private PretService pretService;
    
    @Autowired
    private ReservationService reservationService;
    

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
    public String showPretForm(@PathVariable("idLivre") int idLivre, Model model, HttpSession session) {
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/auth/login";
        }
        
        Livre livre = livreService.getLivreById(idLivre);
        List<Exemplaire> exemplairesDisponibles = exemplaireService.findDisponiblesByLivre(idLivre);
        
        model.addAttribute("livre", livre);
        model.addAttribute("exemplaires", exemplairesDisponibles);
        model.addAttribute("pretForm", new PretForm());
        
        return "livre/pret";
    }
    
    @PostMapping("/pret")
    public String processPret(@ModelAttribute PretForm pretForm, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/auth/login";
        }
        
        Bibliothecaire biblio = (Bibliothecaire) session.getAttribute("bibliothecaire");
        
        try {
            // Vérifications et traitement du prêt
            pretService.processPret(pretForm.getIdExemplaire(), pretForm.getIdAdherent(), biblio.getId_biblio());
            redirectAttributes.addFlashAttribute("success", "Prêt enregistré avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/livre/pret/" + pretForm.getIdLivre();
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/retourner/{idExemplaire}")
    public String showRetourForm(@PathVariable("idExemplaire") int idExemplaire, Model model, HttpSession session) {
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/auth/login";
        }
        
        Exemplaire exemplaire = exemplaireService.getExemplaireById(idExemplaire);
        Pret pretActif = pretService.findPretActifByExemplaire(idExemplaire);
        
        if (pretActif == null) {
            return "redirect:/?error=Aucun prêt en cours pour cet exemplaire";
        }
        
        model.addAttribute("exemplaire", exemplaire);
        model.addAttribute("pret", pretActif);
        
        return "livre/retourner";
    }
    
    @PostMapping("/retourner")
    public String processRetour(@RequestParam("idPret") int idPret, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/auth/login";
        }
        
        Bibliothecaire biblio = (Bibliothecaire) session.getAttribute("bibliothecaire");
        
        try {
            pretService.processRetour(idPret, biblio.getId_biblio());
            redirectAttributes.addFlashAttribute("success", "Retour enregistré avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/prolonger/{idPret}")
    public String showProlongerForm(@PathVariable("idPret") int idPret, Model model, HttpSession session) {
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/auth/login";
        }
        
        Pret pret = pretService.getPretById(idPret);
        if (pret == null) {
            return "redirect:/?error=Prêt introuvable";
        }
        
        model.addAttribute("pret", pret);
        return "livre/prolonger";
    }
    
    @PostMapping("/prolonger")
    public String processProlongement(@RequestParam("idPret") int idPret, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("bibliothecaire") == null) {
            return "redirect:/auth/login";
        }
        
        try {
            pretService.prolongerPret(idPret);
            redirectAttributes.addFlashAttribute("success", "Prolongement effectué avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/reserver/{idLivre}")
    public String showReserverForm(@PathVariable("idLivre") int idLivre, Model model, HttpSession session) {
        Livre livre = livreService.getLivreById(idLivre);
        List<Exemplaire> exemplairesDisponibles = exemplaireService.findDisponiblesByLivre(idLivre);
        
        model.addAttribute("livre", livre);
        model.addAttribute("exemplaires", exemplairesDisponibles);
        
        if (session.getAttribute("adherent") != null) {
            model.addAttribute("adherent", session.getAttribute("adherent"));
        }
        
        return "livre/reserver";
    }
    
    @PostMapping("/reserver")
    public String processReservation(@RequestParam("idExemplaire") int idExemplaire,
                                   @RequestParam("idAdherent") int idAdherent,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session) {
        if (session.getAttribute("adherent") == null && session.getAttribute("bibliothecaire") == null) {
            return "redirect:/auth/login";
        }
        
        try {
            reservationService.createReservation(idExemplaire, idAdherent);
            redirectAttributes.addFlashAttribute("success", "Réservation enregistrée avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/";
    }
    
    // Classe interne pour le formulaire de prêt
    public static class PretForm {
        private int idLivre;
        private int idExemplaire;
        private int idAdherent;
        
        // Getters et Setters
        public int getIdLivre() { return idLivre; }
        public void setIdLivre(int idLivre) { this.idLivre = idLivre; }
        public int getIdExemplaire() { return idExemplaire; }
        public void setIdExemplaire(int idExemplaire) { this.idExemplaire = idExemplaire; }
        public int getIdAdherent() { return idAdherent; }
        public void setIdAdherent(int idAdherent) { this.idAdherent = idAdherent; }
    }
}